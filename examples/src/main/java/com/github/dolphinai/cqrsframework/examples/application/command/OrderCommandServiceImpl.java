package com.github.dolphinai.cqrsframework.examples.application.command;

import com.github.dolphinai.cqrsframework.examples.domain.cmd.OrderCancellationCommand;
import com.github.dolphinai.cqrsframework.examples.domain.cmd.OrderCreatedCommand;
import com.github.dolphinai.cqrsframework.examples.domain.cmd.OrderUpdatedCommand;
import com.github.dolphinai.cqrsframework.core.command.CommandGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import reactor.core.publisher.Mono;

/**
 */
@Service
@Slf4j
public class OrderCommandServiceImpl implements OrderCommandService {

    @Autowired
    private CommandGateway commandGateway;

    @Override
    public Mono<Void> execute(OrderCreatedCommand command) {
        log.info("{}", command);
        return Mono.fromFuture(commandGateway.send(command));
    }

    @Override
    public Mono<Void> execute(OrderUpdatedCommand command) {
        log.info("{}", command);
        return Mono.fromFuture(commandGateway.send(command));
    }

    @Override
    public Mono<Void> execute(OrderCancellationCommand command) {
        log.info("{}", command);
        StandardServletMultipartResolver r;
        return Mono.fromFuture(commandGateway.send(command));
    }
}
