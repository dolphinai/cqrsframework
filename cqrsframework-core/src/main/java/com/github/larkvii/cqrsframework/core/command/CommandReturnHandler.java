package com.github.larkvii.cqrsframework.core.command;

import com.github.larkvii.cqrsframework.core.event.EventGateway;

import java.util.Collection;
import java.util.function.Function;

/**
 *
 */
public final class CommandReturnHandler implements Function<Object, Object> {

  private final EventGateway eventGateway;

  public CommandReturnHandler(EventGateway eventGateway) {
    this.eventGateway = eventGateway;
  }

  @Override
  public Object apply(final Object returnValue) {
    if (returnValue == null) {
      return returnValue;
    }
    Class<?> clazz = returnValue.getClass();
    if (clazz.isArray()) {
      eventGateway.publish((Object[]) returnValue);
    } else if (Collection.class.isAssignableFrom(clazz)) {
      Collection<?> list = (Collection) returnValue;
      eventGateway.publish(list.toArray());
    } else {
      eventGateway.publish(returnValue);
    }
    return returnValue;
  }
}
