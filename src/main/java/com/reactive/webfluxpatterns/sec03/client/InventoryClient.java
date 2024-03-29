package com.reactive.webfluxpatterns.sec03.client;

import com.reactive.webfluxpatterns.sec03.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.reactive.webfluxpatterns.sec03.util.Constants.*;

@Service
public class InventoryClient {
    private final WebClient webClient;

    public InventoryClient(@Value("${sec03.inventory.service}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest inventoryRequest) {
        return this.call(inventoryRequest, DEDUCT);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return this.call(request, RESTORE);
    }

    public Mono<InventoryResponse> call(InventoryRequest inventoryRequest, String endpoint) {
        return this.webClient.post().uri(endpoint)
                .bodyValue(inventoryRequest).retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(buildErrorResponse(inventoryRequest));
    }

    private InventoryResponse buildErrorResponse(InventoryRequest paymentRequest) {
        return InventoryResponse.create(paymentRequest.getProductId(),
                paymentRequest.getQuantity(),
                null,
                Status.FAILED);
    }
}
