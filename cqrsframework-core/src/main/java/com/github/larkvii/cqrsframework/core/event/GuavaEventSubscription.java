package com.github.larkvii.cqrsframework.core.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 *
 */
public final class GuavaEventSubscription implements EventSubscription {

  private EventBus eventBus;

  public GuavaEventSubscription(String name, boolean async) {
    if (async) {
      eventBus = new AsyncEventBus(name, Executors.newFixedThreadPool(2));
    } else {
      eventBus = new EventBus(name);
    }
  }

  @Override
  public void publish(final Object event) {
    eventBus.post(event);
  }

  @Override
  public void subscribe(final Object listener) {
    eventBus.register(listener);
  }

  @Override
  public void unsubscribe(final Object listener) {
    eventBus.unregister(listener);
  }

  @Override
  public void close() throws IOException {
    eventBus = null;
  }
}
