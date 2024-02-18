package com.reactive.webfluxpatterns.sec02.client;
import com.reactive.webfluxpatterns.sec02.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeltaClient implements Client<Flux<FlightResult>, FlightDTO>{

    private WebClient client;


    public DeltaClient(@Value("${sec02.delta.service}") String baseUrl) {
        this.client = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Flux<FlightResult> get(FlightDTO value) {
        this.client.get()
                .uri("{from}/{to}", value.from(), value.to())
                .retrieve()
                .bodyToFlux(FlightResult.class).onErrorResume(error -> Mono.empty());
    }
}

record FlightDTO (String from, String to) {}