package com.github.dolphinai.cqrsframework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *
 */
public final class MessageInterceptorFactory implements Function<Message, Object> {

  private List<MessageInterceptor> interceptors = new ArrayList<>();

  public void addInterceptor(MessageInterceptor interceptor) {
    this.interceptors.add(interceptor);
  }

  @Override
  public Object apply(final Message message) {
    MessageInterceptorChain chain = new MessageInterceptorChainImpl(this.interceptors, 0);
    return chain.proceed(message);
  }
}
