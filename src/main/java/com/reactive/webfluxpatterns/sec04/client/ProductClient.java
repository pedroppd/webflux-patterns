package com.reactive.webfluxpatterns.sec04.client;

import com.reactive.webfluxpatterns.sec04.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient implements Client<Mono<Product>, Integer> {

    private final WebClient client;

    public ProductClient(@Value("${sec01.product.service}") String url) {
        this.client = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public Mono<Product> get(Integer value) {
        return this.client.get().uri("/product/{id}", value)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorResume(ex -> Mono.empty());
    }

    @Override
    public Mono<Product> post(Integer value) {
        return null;
    }
}
