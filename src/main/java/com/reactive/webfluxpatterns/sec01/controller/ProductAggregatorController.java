package com.reactive.webfluxpatterns.sec01.controller;

import com.reactive.webfluxpatterns.sec01.dto.ProductAggregate;
import com.reactive.webfluxpatterns.sec01.services.ProductAggregatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("sec01")
public class ProductAggregatorController {
    private ProductAggregatorService service;
    @GetMapping("product/{id}")
    public Mono<ResponseEntity<ProductAggregate>> get(@PathVariable("id") Integer id) {
        return this.service.aggregate(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
