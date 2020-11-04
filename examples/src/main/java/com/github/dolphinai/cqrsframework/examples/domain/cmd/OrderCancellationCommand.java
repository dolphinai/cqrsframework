package com.github.dolphinai.cqrsframework.examples.domain.cmd;

import com.github.dolphinai.cqrsframework.examples.infrastructure.event.OrderCancellationEvent;
import lombok.Data;

/**
 */
@Data
public class OrderCancellationCommand implements OrderCancellationEvent {

    private String id;

    public static OrderCancellationCommand of(String id) {
        OrderCancellationCommand cmd = new OrderCancellationCommand();
        cmd.setId(id);
        return cmd;
    }
}
