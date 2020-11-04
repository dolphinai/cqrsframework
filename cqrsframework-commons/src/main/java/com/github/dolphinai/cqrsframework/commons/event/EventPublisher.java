package com.github.dolphinai.cqrsframework.commons.event;

/**
 */
public interface EventPublisher {

  void publish(Object event);

}
