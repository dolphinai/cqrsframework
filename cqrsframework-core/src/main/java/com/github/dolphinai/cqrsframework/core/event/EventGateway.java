package com.github.dolphinai.cqrsframework.core.event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public interface EventGateway {

  default void publishAndWait(Object... events) {
    try {
      publish(events).get(10, TimeUnit.SECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new IllegalStateException(e);
    }
  }

  CompletableFuture<Void> publish(Object... events);
}
