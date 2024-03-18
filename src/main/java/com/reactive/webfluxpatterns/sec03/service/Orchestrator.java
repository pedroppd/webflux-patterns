package com.reactive.webfluxpatterns.sec03.service;

import com.reactive.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public abstract class Orchestrator {
    public abstract Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx);
    public abstract Predicate<OrchestrationRequestContext> isSuccess();
    public abstract Consumer<OrchestrationRequestContext> cancel();
}
