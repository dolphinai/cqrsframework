package com.github.larkvii.cqrsframework.core;

import java.util.HashMap;

/**
 *
 */
public final class MessageMetadata extends HashMap<String, Object> {

  public MessageMetadata with(final String name, final Object value) {
    this.put(name, value);
    return this;
  }
}
