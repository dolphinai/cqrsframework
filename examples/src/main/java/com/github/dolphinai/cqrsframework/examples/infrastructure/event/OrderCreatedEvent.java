package com.github.dolphinai.cqrsframework.examples.infrastructure.event;

import java.io.Serializable;

/**
 */
public interface OrderCreatedEvent extends Serializable {

    String getId();

}
