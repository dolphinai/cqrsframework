package com.github.dolphinai.cqrsframework.commons.event.impl;

import lombok.NonNull;
import org.springframework.context.ApplicationListener;

/**
 */
public class SpringEventSubscriptionImpl extends AbstractEventSubscription implements  ApplicationListener<WrappedEvent> {

  public SpringEventSubscriptionImpl() {
    super();
  }

  @Override
  public void onApplicationEvent(@NonNull final WrappedEvent wrappedEvent) {
    if (wrappedEvent != null) {
      onProcessEvent(wrappedEvent.getEvent());
    }
  }
}
