package com.github.dolphinai.cqrsframework.examples.infrastructure.event;

import java.io.Serializable;

/**
 */
public interface OrderCancellationEvent extends Serializable {

    String getId();

}
