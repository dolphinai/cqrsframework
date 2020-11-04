package com.github.dolphinai.cqrsframework.spring.interceptors;

import com.github.dolphinai.cqrsframework.core.event.EventGateway;
import com.github.dolphinai.cqrsframework.core.event.EventMessageEnvelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.util.concurrent.CompletableFuture;

/**
 *
 */
@Slf4j
public final class SpringEventGateway implements EventGateway, ApplicationEventPublisherAware {

  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public CompletableFuture<Void> publish(final Object... events) {
    final EventMessageEnvelope messageGroup = EventMessageEnvelope.of(events);
    log.info("send: {}", messageGroup);
    return CompletableFuture.runAsync(() -> {
      applicationEventPublisher.publishEvent(messageGroup);
    });
  }
}
