package com.github.dolphinai.cqrsframework.common.event.impl;

import com.github.dolphinai.cqrsframework.common.event.EventSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 */
@SuppressWarnings("unchecked")
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
  public <T> void subscribe(final Class<T> eventClass, final Consumer<T> eventProcessor) {
    Objects.requireNonNull(eventProcessor);
    this.listeners.put(eventClass.getName(), eventProcessor);
  }

  @Override
  public <T> void unsubscribe(final Class<T> eventClass) {
    this.listeners.remove(eventClass.getName());
  }

  @Override
  public void close() throws IOException {
    this.listeners.clear();
  }

  @Override
  public void destroy() throws Exception {
    close();
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
