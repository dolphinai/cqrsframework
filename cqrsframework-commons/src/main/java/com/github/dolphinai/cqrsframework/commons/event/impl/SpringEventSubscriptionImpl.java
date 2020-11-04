package com.github.dolphinai.cqrsframework.commons.event.impl;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;

/**
 */
public class SpringEventSubscriptionImpl extends AbstractEventSubscription implements  ApplicationListener<WrappedEvent> {

  public SpringEventSubscriptionImpl() {
    super();
  }

  @Override
  public void onApplicationEvent(@Nullable final WrappedEvent wrappedEvent) {
    if (wrappedEvent != null) {
      onProcessEvent(wrappedEvent.getEvent());
    }
  }
}
