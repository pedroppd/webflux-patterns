package com.reactive.webfluxpatterns.sec02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor(staticName = "create")
public class FlightResult {
    private String airlane;
    private String date;
    private String from;
    private Double price;
    private String to;
}
