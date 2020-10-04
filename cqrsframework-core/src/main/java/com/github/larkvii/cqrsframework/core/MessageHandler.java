package com.github.larkvii.cqrsframework.core;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 */
@SuppressWarnings("unchecked")
public interface MessageHandler<T extends Message> extends Function<T, Object> {

  default MessageHandler<T> next(MessageHandler<T> after) {
    Objects.requireNonNull(after);

    return (T m) -> {
      Object value = this.apply(m);
      List result = new ArrayList();
      if (value != null) {
        if (List.class.isAssignableFrom(value.getClass())) {
          result = (List) value;
        } else {
          result.add(value);
        }
      }
      value = after.apply(m);
      if (value != null) {
        result.add(value);
      }
      return result;
    };
  }

}
