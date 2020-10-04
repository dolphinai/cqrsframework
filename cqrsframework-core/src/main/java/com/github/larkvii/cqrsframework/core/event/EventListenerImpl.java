package com.github.larkvii.cqrsframework.core.event;

import com.github.larkvii.cqrsframework.core.MessageInterceptorFactory;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Objects;

/**
 *
 */
@Slf4j
public class EventListenerImpl implements EventListener, InitializingBean, DisposableBean {

  private final EventSubscription eventSubscription;
  private final MessageInterceptorFactory messageInterceptorFactory;

  public EventListenerImpl(EventSubscription eventSubscription, MessageInterceptorFactory messageInterceptorFactory) {
    this.eventSubscription = eventSubscription;
    this.messageInterceptorFactory = messageInterceptorFactory;
  }

  @Subscribe
  @Override
  public void accept(final EventMessageEnvelope messageEnvelope) {
    Objects.requireNonNull(messageEnvelope);
    log.info("{}", messageEnvelope);
    try {
      onProcess(messageEnvelope.getMessages());
    } catch (Exception e) {
      log.error("Failed to process events", e);
    }
  }

  protected void onProcess(EventMessage[] messages) {
    for (int i = 0; i < messages.length; i++) {
      messageInterceptorFactory.apply(messages[i]);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.eventSubscription.subscribe(this);
  }

  @Override
  public void destroy() throws Exception {
    this.eventSubscription.unsubscribe(this);
  }
}
