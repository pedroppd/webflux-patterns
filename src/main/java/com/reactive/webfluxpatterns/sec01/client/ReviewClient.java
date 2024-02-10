package com.reactive.webfluxpatterns.sec01.client;

import com.reactive.webfluxpatterns.sec01.dto.ProductResponse;
import com.reactive.webfluxpatterns.sec01.dto.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

public class ReviewClient implements Client<Mono<List<Review>>, Integer>{

    private final WebClient client;

    public ReviewClient(@Value("${sec01.review.service}") String url) {
        this.client = WebClient.builder().baseUrl(url).build();

    }
    @Override
    public Mono<List<Review>> get(Integer value) {
        return this.client.get().uri("{id}", value)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .onErrorReturn(Collections.emptyList());
    }
}
