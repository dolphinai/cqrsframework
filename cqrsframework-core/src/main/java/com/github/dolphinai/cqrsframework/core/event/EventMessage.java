package com.github.dolphinai.cqrsframework.core.event;

import com.github.dolphinai.cqrsframework.core.Message;
import com.github.dolphinai.cqrsframework.commons.util.IdGenerator;
import lombok.*;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@ToString(callSuper = true)
public final class EventMessage extends Message {

  @Setter
  private String id = IdGenerator.uuid().next();
  @Setter
  private Long timestamp = System.currentTimeMillis();

  public EventMessage() {
    super();
  }

  public EventMessage(final Object payload) {
    super(payload);
  }

  public static final Stream<EventMessage> of(final Object... events) {
    Objects.requireNonNull(events);
    Stream.Builder<EventMessage> builder = Stream.builder();
    for (int i = 0; i < events.length; i++) {
      builder.add(new EventMessage(events[i]));
    }
    return builder.build();
  }


}
