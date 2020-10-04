package com.github.larkvii.cqrsframework.examples.application.query;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 */
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    @Override
    public Flux<OrderVo> findAll() {
        return Flux.empty();
    }

    @Override
    public Mono<OrderDetailVo> findById(String id) {
        return Mono.empty();
    }
}
