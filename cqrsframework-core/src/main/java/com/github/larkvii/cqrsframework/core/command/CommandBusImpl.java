package com.github.larkvii.cqrsframework.core.command;

import com.github.larkvii.cqrsframework.core.MessageHandlerInterceptor;
import com.github.larkvii.cqrsframework.core.MessageInterceptorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 *
 */
@Slf4j
public final class CommandBusImpl implements CommandBus {

  private MessageInterceptorFactory messageInterceptorFactory;

  public CommandBusImpl(MessageInterceptorFactory messageInterceptorFactory) {
    this.messageInterceptorFactory = messageInterceptorFactory;
  }

  @Override
  public void post(final CommandMessage message) {
    Objects.requireNonNull(message);
    log.info("command: {}", message);
    try {
      messageInterceptorFactory.apply(message);
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
