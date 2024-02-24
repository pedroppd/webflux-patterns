package com.reactive.webfluxpatterns.sec02.client;

import com.reactive.webfluxpatterns.sec02.dto.FlightDTO;
import com.reactive.webfluxpatterns.sec02.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JetBlue implements Client<Flux<FlightResult>, FlightDTO> {

    private final static String JETBLUE = "JETBLUE";
    private WebClient client;


    public JetBlue(@Value("${sec02.jetblue.service}") String baseUrl) {
        this.client = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Flux<FlightResult> get(FlightDTO value) {
        return this.client.get()
                .uri("/jetblue/{from}/{to}", value.from(), value.to())
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .doOnNext(fr -> this.normalize(fr, value.from(), value.to()))
                .onErrorResume(error -> Flux.empty());
    }

    @Override
    public Flux<FlightResult> post(FlightDTO value) {
        return null;
    }

    private void normalize(FlightResult result, String from, String to) {
        result.setTo(to);
        result.setFrom(from);
        result.setAirlane(JETBLUE);
    }
}