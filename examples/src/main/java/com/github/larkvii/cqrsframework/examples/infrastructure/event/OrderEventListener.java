package com.github.larkvii.cqrsframework.examples.infrastructure.event;

import com.github.larkvii.cqrsframework.annotation.EventHandler;
import com.github.larkvii.cqrsframework.examples.domain.aggregate.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 */
@Component
@Slf4j
public class OrderEventListener {

    @EventHandler
    public void on(Order event) {
        log.info("Domain -> {}", event);
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        log.info("{}", event);
    }

    @EventHandler
    public void on(OrderUpdatedEvent event) {
        log.info("{}", event);
    }

    @EventHandler
    public void on(OrderCancellationEvent event) {
        log.info("{}", event);
    }
}
