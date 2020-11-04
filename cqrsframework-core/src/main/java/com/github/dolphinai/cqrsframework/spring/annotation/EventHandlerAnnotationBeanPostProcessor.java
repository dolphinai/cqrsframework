package com.github.dolphinai.cqrsframework.spring.annotation;

import com.github.dolphinai.cqrsframework.annotation.EventHandler;
import com.github.dolphinai.cqrsframework.spring.interceptors.EventMessageHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Slf4j
public final class EventHandlerAnnotationBeanPostProcessor extends AbstractAnnotationBeanPostProcessor {

  private EventMessageHandlerAdapter messageHandlerAdapter;

  public EventHandlerAnnotationBeanPostProcessor(EventMessageHandlerAdapter messageHandlerAdapter) {
    this.messageHandlerAdapter = messageHandlerAdapter;
  }

  @Override
  protected Set<Class<? extends Annotation>> getSupportedMessageTypes() {
    Set<Class<? extends Annotation>> sets = new HashSet<>();
    sets.add(EventHandler.class);
    return Collections.unmodifiableSet(sets);
  }

  @Override
  protected void createProxy(final Object bean, final Method method, final Class<? extends Annotation> annotation) {
    Class<?> payloadType = method.getParameterTypes()[0];
    messageHandlerAdapter.addHandler(payloadType, bean, method);
    log.info("##### {} method={}, annotation={}", bean, method, annotation);
  }
}
