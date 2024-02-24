package com.reactive.webfluxpatterns.sec02.controllers;

import com.reactive.webfluxpatterns.sec02.dto.FlightResult;
import com.reactive.webfluxpatterns.sec02.services.FlightSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
@RestController
@RequestMapping("sec02")
public class FlightController {

    @Autowired
    private FlightSearchService service;

    @GetMapping(value = "flights/{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> get(@PathVariable(name = "from") String from, @PathVariable(name = "to") String to) {
        return service.search(from, to);
    }


}
