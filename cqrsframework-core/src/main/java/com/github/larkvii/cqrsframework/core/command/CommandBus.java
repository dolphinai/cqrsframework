package com.github.larkvii.cqrsframework.core.command;

/**
 *
 */
public interface CommandBus {

  void post(final CommandMessage message);

}
