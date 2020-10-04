package com.github.larkvii.cqrsframework.examples.domain.cmd;

import com.github.larkvii.cqrsframework.examples.infrastructure.event.OrderCreatedEvent;
import lombok.Data;

/**
 */
@Data
public class OrderCreatedCommand implements OrderCreatedEvent {

    private String id;

}
