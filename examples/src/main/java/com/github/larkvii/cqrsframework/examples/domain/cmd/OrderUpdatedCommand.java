package com.github.larkvii.cqrsframework.examples.domain.cmd;

import com.github.larkvii.cqrsframework.examples.infrastructure.event.OrderUpdatedEvent;
import lombok.Data;

/**
 */
@Data
public class OrderUpdatedCommand implements OrderUpdatedEvent {

    private String id;

}
