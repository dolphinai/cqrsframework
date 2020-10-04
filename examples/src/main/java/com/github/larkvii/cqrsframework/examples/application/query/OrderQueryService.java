package com.github.larkvii.cqrsframework.examples.application.query;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 */
public interface OrderQueryService {

    Flux<OrderVo> findAll();

    Mono<OrderDetailVo> findById(String id);

}
