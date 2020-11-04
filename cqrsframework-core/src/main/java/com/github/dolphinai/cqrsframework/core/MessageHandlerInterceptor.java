package com.github.dolphinai.cqrsframework.core;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

/**
 *
 */
@SuppressWarnings("unchecked")
public final class MessageHandlerInterceptor implements MessageInterceptor {

  private final MessageHandlerAdapter messageHandlerAdapter;

  public MessageHandlerInterceptor(final MessageHandlerAdapter messageHandlerAdapter) {
    this.messageHandlerAdapter = messageHandlerAdapter;
  }

  @Override
  public Object apply(final Message message, final MessageInterceptorChain chain) {
    Object result = messageHandlerAdapter.apply(message);
    chain.proceed(message);
    return result;
  }
}
