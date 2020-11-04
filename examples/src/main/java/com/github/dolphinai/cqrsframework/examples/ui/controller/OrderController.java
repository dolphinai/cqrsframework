package com.github.dolphinai.cqrsframework.examples.ui.controller;

import com.github.dolphinai.cqrsframework.examples.application.query.OrderDetailVo;
import com.github.dolphinai.cqrsframework.examples.domain.cmd.OrderCreatedCommand;
import com.github.dolphinai.cqrsframework.examples.domain.cmd.OrderUpdatedCommand;
import com.github.dolphinai.cqrsframework.commons.ResultMap;
import com.github.dolphinai.cqrsframework.examples.application.command.OrderCommandService;
import com.github.dolphinai.cqrsframework.examples.application.query.OrderQueryService;
import com.github.dolphinai.cqrsframework.examples.application.query.OrderVo;
import com.github.dolphinai.cqrsframework.examples.domain.cmd.OrderCancellationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderCommandService orderCommandService;
    @Autowired
    private OrderQueryService orderQueryService;

    @GetMapping
    public Flux<OrderVo> list() {
        return orderQueryService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<OrderDetailVo> getById(@PathVariable("id") String id) {
        return orderQueryService.findById(id);
    }

    @PostMapping
    public ResultMap create(String id, @RequestBody OrderCreatedCommand command) {
        orderCommandService.execute(command);
        return ResultMap.success();
    }

    @PutMapping("/{id}")
    public ResultMap update(@PathVariable("id") String id, @RequestBody OrderUpdatedCommand command) {
        orderCommandService.execute(command);
        return ResultMap.success();
    }

    @DeleteMapping("/{id}")
    public ResultMap delete(@PathVariable("id") String id) {
        orderCommandService.execute(OrderCancellationCommand.of(id));
        return ResultMap.success();
    }

}
