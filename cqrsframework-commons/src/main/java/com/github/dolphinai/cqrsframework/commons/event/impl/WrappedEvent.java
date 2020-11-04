package com.github.dolphinai.cqrsframework.commons.event.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 */
public final class WrappedEvent extends ApplicationContextEvent {

  private final Object event;

  public WrappedEvent(final ApplicationContext source, final Object eventObject) {
    super(source);
    this.event = eventObject;
  }

  public Object getEvent() {
    return event;
  }
}
