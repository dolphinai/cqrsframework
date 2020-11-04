package com.github.dolphinai.cqrsframework.examples.domain.aggregate;

import com.github.dolphinai.cqrsframework.examples.domain.cmd.OrderCreatedCommand;
import com.github.dolphinai.cqrsframework.examples.domain.cmd.OrderUpdatedCommand;
import com.github.dolphinai.cqrsframework.examples.infrastructure.event.OrderUpdatedEvent;
import com.github.dolphinai.cqrsframework.annotation.CommandHandler;
import com.github.dolphinai.cqrsframework.annotation.Aggregate;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 */
@Aggregate
@Slf4j
@Getter
@ToString
public class Order {

    private String id;

    public Order() {

    }

    @CommandHandler
    public Order(OrderCreatedCommand command) {
        log.info("{}", command);
        this.id = command.getId();
        //DomainFactory.apply((OrderCreatedEvent)command);
    }

    @CommandHandler
    public void handle(OrderCreatedCommand command) {
        log.info("handle {}", command);
        this.id = command.getId();
    }

    @CommandHandler
    public OrderUpdatedEvent handle(OrderUpdatedCommand command) {
        log.info("handle {}, {}", command, this.id);
        this.id = command.getId();
        return command;
    }
}
