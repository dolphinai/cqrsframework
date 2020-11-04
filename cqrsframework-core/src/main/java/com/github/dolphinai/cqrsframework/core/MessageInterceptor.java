package com.github.dolphinai.cqrsframework.core;

import java.util.function.BiFunction;

/**
 *
 */
public interface MessageInterceptor extends BiFunction<Message, MessageInterceptorChain, Object> {

}
