package com.reactive.webfluxpatterns.sec01.client;

import com.reactive.webfluxpatterns.sec01.dto.ProductResponse;
import com.reactive.webfluxpatterns.sec01.dto.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient implements Client<Mono<ProductResponse>, Integer> {

    private final WebClient client;

    public ProductClient(@Value("${sec01.product.service}") String url) {
        this.client = WebClient.builder().baseUrl(url).build();
    }

    @Override
    public Mono<ProductResponse> get(Integer value) {
        return this.client.get().uri("/product/{id}", value)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .onErrorResume(ex -> Mono.empty());
    }
}
