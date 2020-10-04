package com.github.larkvii.cqrsframework.core;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 */
@Getter
@ToString
public abstract class Message implements Serializable {

  private MessageMetadata metadata;
  private Class<?> payloadType;
  private Object payload;

  public Message() {
    this.metadata = new MessageMetadata();
  }

  public Message(Object payload) {
    this();
    setPayload(payload);
  }

  public void setPayload(final Object payload) {
    this.payload = payload;
    this.payloadType = payload.getClass();
  }
}
