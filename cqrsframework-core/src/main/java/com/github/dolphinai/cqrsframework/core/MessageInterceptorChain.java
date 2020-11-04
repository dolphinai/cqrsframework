package com.github.dolphinai.cqrsframework.core;

/**
 *
 */
public interface MessageInterceptorChain {

  Object proceed(Message message);
}
