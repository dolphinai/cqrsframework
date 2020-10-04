package com.github.larkvii.cqrsframework.examples.infrastructure.event;

import java.io.Serializable;

/**
 */
public interface OrderCancellationEvent extends Serializable {

    String getId();

}
