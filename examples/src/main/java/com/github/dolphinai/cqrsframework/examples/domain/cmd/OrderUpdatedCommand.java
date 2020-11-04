package com.github.dolphinai.cqrsframework.examples.domain.cmd;

import com.github.dolphinai.cqrsframework.examples.infrastructure.event.OrderUpdatedEvent;
import lombok.Data;

/**
 */
@Data
public class OrderUpdatedCommand implements OrderUpdatedEvent {

    private String id;

}
