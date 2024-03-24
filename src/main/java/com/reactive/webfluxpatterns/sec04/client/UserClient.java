package com.reactive.webfluxpatterns.sec04.client;

import com.reactive.webfluxpatterns.sec04.dto.PaymentRequest;
import com.reactive.webfluxpatterns.sec04.dto.PaymentResponse;
import com.reactive.webfluxpatterns.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.reactive.webfluxpatterns.sec03.util.Constants.DEDUCT;
import static com.reactive.webfluxpatterns.sec03.util.Constants.REFUND;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${sec03.user.service}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<PaymentResponse> deduct(PaymentRequest paymentRequest) {
        return this.call(paymentRequest, DEDUCT);
    }

    public Mono<PaymentResponse> refund(PaymentRequest paymentRequest) {
        return this.call(paymentRequest, REFUND);
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
