package com.github.dolphinai.cqrsframework.spring.annotation;

import com.github.dolphinai.cqrsframework.annotation.Aggregate;
import com.github.dolphinai.cqrsframework.annotation.CommandHandler;
import com.github.dolphinai.cqrsframework.spring.interceptors.CommandMessageHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@Slf4j
public final class AggregateContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

  private CommandMessageHandlerAdapter messageHandlerAdapter;

  public AggregateContextRefreshedListener(CommandMessageHandlerAdapter messageHandlerAdapter) {
    this.messageHandlerAdapter = messageHandlerAdapter;
  }

  @Override
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    final ApplicationContext context;
    if (event.getApplicationContext().getParent() != null) {
      context = event.getApplicationContext().getParent();
    } else {
      context = event.getApplicationContext();
    }
    // Aggregate
    final Map<String, Object> beans = context.getBeansWithAnnotation(Aggregate.class);
    beans.forEach((name, bean) -> {
      Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
      prepareAggregateBean(context, name, targetClass);
    });
  }

  protected void prepareAggregateBean(final BeanFactory beanFactory, final String name, final Class<?> beanClass) {
    // constructors
    List<Executable> actions = new ArrayList<>();
    List<Constructor> ctors = Stream.of(beanClass.getConstructors()).filter(c -> c.getAnnotation(CommandHandler.class) != null).collect(Collectors.toList());
    actions.addAll(ctors);

    // methods
    List<Method> methods = Stream.of(beanClass.getDeclaredMethods()).filter(c -> {
      return c.getAnnotation(CommandHandler.class) != null;
    }).collect(Collectors.toList());
    actions.addAll(methods);

    log.info(">> actions: {}", actions);
    if (actions != null && !actions.isEmpty()) {
      actions.forEach(e -> {
        Class<?> payloadType = e.getParameterTypes()[0];
        messageHandlerAdapter.addHandler(payloadType, beanFactory, name, e);
      });
    }
  }
}
