package com.reactive.webfluxpatterns.sec04.service;

import com.reactive.webfluxpatterns.sec04.client.UserClient;
import com.reactive.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.reactive.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PaymentOrchestrator extends Orchestrator {
    @Autowired
    private UserClient client;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return this.client.deduct(ctx.getPaymentRequest())
                .doOnNext(ctx::setPaymentResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Status.SUCCESS.equals(ctx.getPaymentResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getPaymentRequest)
                .flatMap(this.client::refund)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
