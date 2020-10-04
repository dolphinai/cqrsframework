package com.github.larkvii.cqrsframework.examples.infrastructure.event;

import java.io.Serializable;

/**
 */
public interface OrderUpdatedEvent extends Serializable {

    String getId();

}
