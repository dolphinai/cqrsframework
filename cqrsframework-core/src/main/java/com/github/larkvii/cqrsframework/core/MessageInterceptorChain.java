package com.github.larkvii.cqrsframework.core;

import java.util.List;
import java.util.function.Function;

/**
 *
 */
public interface MessageInterceptorChain {

  Object proceed(Message message);
}
