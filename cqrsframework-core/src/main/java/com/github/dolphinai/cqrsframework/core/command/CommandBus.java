package com.github.dolphinai.cqrsframework.core.command;

/**
 *
 */
public interface CommandBus {

  void post(final CommandMessage message);

}
