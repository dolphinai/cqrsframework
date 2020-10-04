package com.github.larkvii.cqrsframework.core.event;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 *
 */
@Slf4j
public class EventGatewayImpl implements EventGateway {


  private final EventSubscription eventSubscription;

  public EventGatewayImpl(EventSubscription eventSubscription) {
    this.eventSubscription = eventSubscription;
  }

  @Override
  public CompletableFuture<Void> publish(Object... events) {
    final EventMessageEnvelope messageEnvelope = EventMessageEnvelope.of(events);
    log.info("send: {}", messageEnvelope);
    return CompletableFuture.runAsync(() -> eventSubscription.publish(messageEnvelope));
  }

}
