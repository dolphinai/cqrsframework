package com.github.dolphinai.cqrsframework.core.command;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface CommandGateway {

  default void sendAndWait(final Object command) {
    try {
      this.send(command).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new IllegalStateException(e);
    }
  }

  CompletableFuture<Void> send(final Object command);

}
