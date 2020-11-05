package com.github.dolphinai.cqrsframework.spring.annotation;

import com.github.dolphinai.cqrsframework.annotation.EventHandler;
import com.github.dolphinai.cqrsframework.commons.spring.MethodAnnotatedPostProcessor;
import com.github.dolphinai.cqrsframework.spring.interceptors.EventMessageHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 *
 */
@Slf4j
public final class EventHandlerAnnotationBeanPostProcessor extends MethodAnnotatedPostProcessor<EventHandler> {

  private EventMessageHandlerAdapter messageHandlerAdapter;

  public EventHandlerAnnotationBeanPostProcessor(EventMessageHandlerAdapter messageHandlerAdapter) {
    super(EventHandler.class);
    this.messageHandlerAdapter = messageHandlerAdapter;
  }

  @Override
  protected void createProxy(final Object bean, final Method method) {
    super.createProxy(bean, method);
    Class<?> payloadType = method.getParameterTypes()[0];
    messageHandlerAdapter.addHandler(payloadType, bean, method);
    log.info("##### {} method={}, bean={}", EventHandler.class, bean, method);
  }
}
