package com.github.larkvii.cqrsframework.core;

import java.util.List;

/**
 *
 */
public class MessageInterceptorChainImpl implements MessageInterceptorChain {

  private final List<MessageInterceptor> interceptors;
  private final int index;

  public MessageInterceptorChainImpl(final List<MessageInterceptor> interceptors, final int index) {
    this.interceptors = interceptors;
    this.index = index;
  }

  public Object proceed(Message message) {
    if (index < interceptors.size()) {
      MessageInterceptorChain next = new MessageInterceptorChainImpl(interceptors, index + 1);
      MessageInterceptor interceptor = interceptors.get(index);
      return interceptor.apply(message, next);
    }
    return null;
  }
}
