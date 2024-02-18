package com.reactive.webfluxpatterns.sec02.services;

import com.reactive.webfluxpatterns.sec02.client.Delta;
import com.reactive.webfluxpatterns.sec02.client.Frontier;
import com.reactive.webfluxpatterns.sec02.client.JetBlue;
import com.reactive.webfluxpatterns.sec02.dto.FlightDTO;
import com.reactive.webfluxpatterns.sec02.dto.FlightResult;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FlightSearchService {

    @Autowired
    private JetBlue jetBlue;
    @Autowired
    private Delta delta;
    @Autowired
    private Frontier frontier;

    private static final Long DURATION = 3L;

    public Flux<FlightResult> search(String from, String to) {
        return Flux.merge(
                jetBlue.get(new FlightDTO(from, to)),
                delta.get(new FlightDTO(from, to)),
                frontier.post(new FlightDTO(from, to))
        ).take(Duration.ofSeconds(DURATION));
    }
}
