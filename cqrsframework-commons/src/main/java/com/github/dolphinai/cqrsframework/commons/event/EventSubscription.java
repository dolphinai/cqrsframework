package com.github.dolphinai.cqrsframework.commons.event;

import java.io.Closeable;
import java.util.function.Consumer;

/**
 * Event subscriber.
 */
public interface EventSubscription extends Closeable {

  <T> void subscribe(Class<T> eventClass, Consumer<T> eventProcessor);

  <T> void unsubscribe(Class<T> eventClass);

}
