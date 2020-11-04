package com.github.dolphinai.cqrsframework.examples.infrastructure.event;

import java.io.Serializable;

/**
 */
public interface OrderUpdatedEvent extends Serializable {

    String getId();

}
