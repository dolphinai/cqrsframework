package com.github.dolphinai.cqrsframework.examples.infrastructure.event;

import com.github.dolphinai.cqrsframework.examples.domain.aggregate.Order;
import com.github.dolphinai.cqrsframework.annotation.EventHandler;
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
