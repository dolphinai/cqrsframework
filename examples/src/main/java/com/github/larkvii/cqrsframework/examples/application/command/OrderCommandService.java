package com.github.larkvii.cqrsframework.examples.application.command;

import com.github.larkvii.cqrsframework.examples.domain.cmd.OrderCancellationCommand;
import com.github.larkvii.cqrsframework.examples.domain.cmd.OrderCreatedCommand;
import com.github.larkvii.cqrsframework.examples.domain.cmd.OrderUpdatedCommand;
import reactor.core.publisher.Mono;

/**
 */
public interface OrderCommandService {

    Mono<Void> execute(OrderCreatedCommand command);

    Mono<Void> execute(OrderUpdatedCommand command);

    Mono<Void> execute(OrderCancellationCommand command);
}
