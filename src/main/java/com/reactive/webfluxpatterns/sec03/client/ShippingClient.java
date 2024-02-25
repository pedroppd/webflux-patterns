package com.reactive.webfluxpatterns.sec03.client;

import com.reactive.webfluxpatterns.sec03.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ShippingClient {

    private final WebClient webClient;

    public ShippingClient(@Value("${sec03.shipping.service}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest shippingRequest) {
        return this.call(shippingRequest, "schedule");
    }

    public Mono<ShippingResponse> restore(ShippingRequest shippingRequest) {
        return this.call(shippingRequest, "restore");
    }

    public Mono<ShippingResponse> call(ShippingRequest shippingRequest, String endpoint) {
        return this.webClient.post().uri(endpoint)
                .bodyValue(shippingRequest).retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(this.buildErrorResponse(shippingRequest));
    }

    private ShippingResponse buildErrorResponse(ShippingRequest shippingRequest) {
        return ShippingResponse.create(shippingRequest.getOrderId(),
                shippingRequest.getQuantity(),
                Status.FAILED,
                null, null);
    }
}
