package com.github.larkvii.cqrsframework.commons.event.impl;

import org.springframework.context.ApplicationListener;

/**
 */
public class SpringEventSubscriptionImpl extends AbstractEventSubscription implements  ApplicationListener<WrappedEvent> {

  public SpringEventSubscriptionImpl() {
    super();
  }

  @Override
  public void onApplicationEvent(final WrappedEvent wrappedEvent) {
    if (wrappedEvent != null) {
      onProcessEvent(wrappedEvent.getEvent());
    }
  }
}
