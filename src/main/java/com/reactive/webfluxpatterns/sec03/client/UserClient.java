package com.reactive.webfluxpatterns.sec03.client;

import com.reactive.webfluxpatterns.sec03.dto.PaymentRequest;
import com.reactive.webfluxpatterns.sec03.dto.PaymentResponse;
import com.reactive.webfluxpatterns.sec03.dto.Product;
import com.reactive.webfluxpatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${sec03.user.service}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<PaymentResponse> deduct(PaymentRequest paymentRequest) {
        return this.call(paymentRequest, "deduct");
    }

    public Mono<PaymentResponse> refund(PaymentRequest paymentRequest) {
        return this.call(paymentRequest, "refund");
    }

    public Mono<PaymentResponse> call(PaymentRequest paymentRequest, String endpoint) {
        return this.webClient.post().uri(endpoint)
                .bodyValue(paymentRequest).retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(buildErrorResponse(paymentRequest));
    }

    private PaymentResponse buildErrorResponse(PaymentRequest paymentRequest) {
        return PaymentResponse.create(paymentRequest.getUserId(), null, paymentRequest.getAmount(), Status.FAILED);
    }
}
