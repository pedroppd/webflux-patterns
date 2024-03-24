package com.reactive.webfluxpatterns.sec04.client;

import com.reactive.webfluxpatterns.sec04.dto.ShippingRequest;
import com.reactive.webfluxpatterns.sec04.dto.ShippingResponse;
import com.reactive.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.reactive.webfluxpatterns.sec03.util.Constants.CANCEL;
import static com.reactive.webfluxpatterns.sec03.util.Constants.SCHEDULE;

@Service
public class ShippingClient {

    private final WebClient client;

    public ShippingClient(@Value("${sec03.shipping.service}") String baseUrl){
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest request){
        return this.callShippingService(SCHEDULE, request);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest request){
        return this.callShippingService(CANCEL, request);
    }

    private Mono<ShippingResponse> callShippingService(String endPoint, ShippingRequest request){
        return this.client
                .post()
                .uri(endPoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private ShippingResponse buildErrorResponse(ShippingRequest request){
        return ShippingResponse.create(
                request.getOrderId(),
                request.getQuantity(),
                Status.FAILED,
                null,
                null
        );
    }
}
