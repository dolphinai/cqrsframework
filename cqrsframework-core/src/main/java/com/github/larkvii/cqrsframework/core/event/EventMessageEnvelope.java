package com.github.larkvii.cqrsframework.core.event;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 */
@ToString
@Getter
public class EventMessageEnvelope implements Serializable {

  private EventMessage[] messages;

  public static EventMessageEnvelope of(Object... events) {
    EventMessage[] array = new EventMessage[events.length];
    for (int i = 0; i < events.length; i++) {
      array[i] = new EventMessage(events[i]);
    }
    EventMessageEnvelope instance = new EventMessageEnvelope();
    instance.messages = array;
    return instance;
  }
}
