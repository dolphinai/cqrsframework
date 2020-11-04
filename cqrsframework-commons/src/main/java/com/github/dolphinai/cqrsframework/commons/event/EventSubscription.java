package com.github.dolphinai.cqrsframework.commons.event;

import java.util.function.Consumer;

/**
 */
public interface EventSubscription {

  <T> void subscribe(Class<T> eventClass, Consumer<T> handler);

  <T> void unsubscribe(Class<T> eventClass);

}
