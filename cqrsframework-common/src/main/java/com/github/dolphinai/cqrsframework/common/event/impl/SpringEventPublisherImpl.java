package com.github.dolphinai.cqrsframework.common.event.impl;

import com.github.dolphinai.cqrsframework.common.event.EventPublisher;
import org.springframework.context.ApplicationContext;

/**
 */
public final class SpringEventPublisherImpl implements EventPublisher {

  private final ApplicationContext applicationContext;

  public SpringEventPublisherImpl(final ApplicationContext context) {
    this.applicationContext = context;
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void publish(final Object event) {
    applicationContext.publishEvent(new WrappedEvent(applicationContext, event));
  }

}
