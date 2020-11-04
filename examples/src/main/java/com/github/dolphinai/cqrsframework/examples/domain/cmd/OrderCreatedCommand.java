package com.github.dolphinai.cqrsframework.examples.domain.cmd;

import com.github.dolphinai.cqrsframework.examples.infrastructure.event.OrderCreatedEvent;
import lombok.Data;

/**
 */
@Data
public class OrderCreatedCommand implements OrderCreatedEvent {

    private String id;

}
