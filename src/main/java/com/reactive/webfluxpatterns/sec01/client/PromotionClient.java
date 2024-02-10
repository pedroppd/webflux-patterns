package com.reactive.webfluxpatterns.sec01.client;

import com.reactive.webfluxpatterns.sec01.dto.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class PromotionClient implements Client<Mono<PromotionResponse>, Integer>{
    private final PromotionResponse noPromotion = PromotionResponse.create(-1, "no promotion", 0.0, LocalDate.now());
    private final WebClient client;

    public PromotionClient(@Value("${sec01.promotion.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public Mono<PromotionResponse> get(Integer value) {
        return this.client
                .get()
                .uri("{id}", value)
                .retrieve()
                .bodyToMono(PromotionResponse.class)
                .onErrorReturn(noPromotion);
    }
}
