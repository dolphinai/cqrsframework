package com.github.larkvii.cqrsframework.core.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.CompletableFuture;

@Slf4j
public final class CommandGatewayImpl implements CommandGateway, InitializingBean, DisposableBean {

  private final CommandBus commandBus;

  public CommandGatewayImpl(final CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }

  @Override
  public CompletableFuture<Void> send(final Object command) {
    CommandMessage message = CommandMessage.of(command);
    log.info("send: {}", message);
    return CompletableFuture.runAsync(() -> commandBus.post(message));
  }

  @Override
  public void destroy() throws Exception {

  }
}
