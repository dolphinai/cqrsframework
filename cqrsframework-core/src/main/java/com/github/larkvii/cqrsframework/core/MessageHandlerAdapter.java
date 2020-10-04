package com.github.larkvii.cqrsframework.core;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 */
@SuppressWarnings("unchecked")
public interface MessageHandlerAdapter<T extends Message> extends Function<T, Object> {

  Optional<MessageHandler> getHandler(Class<?> payloadType);

  default boolean canHandle(final Object message) {
    return true;
  }

  default Object apply(final T message) {
    if (!canHandle(message)) {
      return null;
    }
    Optional<MessageHandler> handler = getHandler(message.getPayloadType());
    Object result = null;
    if (handler.isPresent()) {
      result = onApply(handler.get(), message);
    }
    return result;
  }

  default Object onApply(final Function<T, Object> handler, final T message) {
    return handler.apply(message);
  }
}
