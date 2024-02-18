package com.reactive.webfluxpatterns.sec02.client;

import com.reactive.webfluxpatterns.sec02.dto.FlightDTO;
import com.reactive.webfluxpatterns.sec02.dto.FlightResult;
import com.reactive.webfluxpatterns.sec02.dto.FrontierRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class Frontier implements Client<Flux<FlightResult>, FlightDTO> {

    private final static String JETBLUE = "JETBLUE";
    private WebClient client;


    public Frontier(@Value("${sec02.jetblue.service}") String baseUrl) {
        this.client = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Flux<FlightResult> get(FlightDTO value) {
        return null;
    }

    @Override
    public Flux<FlightResult> post(FlightDTO value) {
        return this.client.post()
                .bodyValue(FrontierRequestDTO.create(value.from(), value.to()))
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(error -> Mono.empty());
    }
}