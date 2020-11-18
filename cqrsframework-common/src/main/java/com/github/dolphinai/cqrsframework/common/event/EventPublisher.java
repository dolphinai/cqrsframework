package com.github.dolphinai.cqrsframework.common.event;

/**
 */
public interface EventPublisher {

  void publish(Object event);

}
