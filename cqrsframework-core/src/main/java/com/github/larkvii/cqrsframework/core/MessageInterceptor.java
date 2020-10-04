package com.github.larkvii.cqrsframework.core;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 */
public interface MessageInterceptor extends BiFunction<Message, MessageInterceptorChain, Object> {

}
