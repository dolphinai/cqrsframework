package com.github.larkvii.cqrsframework.commons.event.impl;

import com.github.larkvii.cqrsframework.commons.event.EventSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 */
public abstract class AbstractEventSubscription implements EventSubscription, DisposableBean {

  private static final Logger log = LoggerFactory.getLogger(AbstractEventSubscription.class);
  private final Map<String, Consumer> listeners;

  public AbstractEventSubscription() {
    this.listeners = new ConcurrentHashMap<>();
  }

  public Map<String, Consumer> getListeners() {
    return listeners;
  }

  @Override
  public <T> void subscribe(final Class<T> eventClass, final Consumer<T> handler) {
    Objects.requireNonNull(handler);
    this.listeners.put(eventClass.getName(), handler);
  }

  @Override
  public <T> void unsubscribe(final Class<T> eventClass) {
    this.listeners.remove(eventClass.getName());
  }

  @Override
  public void destroy() throws Exception {
    this.listeners.clear();
  }

  protected void onProcessEvent(Object event) {
    Objects.requireNonNull(event);
    String name = event.getClass().getName();
    if (listeners.containsKey(name)) {
      if(log.isDebugEnabled()) {
        log.debug("The event '{}' be triggered", name);
      }
      final Consumer handler = listeners.get(name);
      try {
        handler.accept(event);
      } catch (Exception e) {
        log.error("Failed to handle the event:" + name, e);
      }
    } else {
      log.warn("The event '{}' not be subscribed", name);
    }
  }
}
