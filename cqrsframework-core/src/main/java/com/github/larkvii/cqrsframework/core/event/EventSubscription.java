package com.github.larkvii.cqrsframework.core.event;

import java.io.Closeable;

/**
 *
 */
public interface EventSubscription extends Closeable {

  void publish(Object event);

  void subscribe(Object listener);

  void unsubscribe(Object listener);
}
