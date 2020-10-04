package com.github.larkvii.cqrsframework.core.command;

import com.github.larkvii.cqrsframework.core.Message;
import lombok.*;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
public final class CommandMessage extends Message {

  public CommandMessage() {
    super();
  }

  public CommandMessage(final Object payload) {
    super(payload);
  }

  public static final CommandMessage of(final Object command) {
    Objects.requireNonNull(command);
    return new CommandMessage(command);
  }

}
