package com.reactive.webfluxpatterns.sec01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor(staticName = "create")
public class PromotionResponse {

    private Integer id;
    private String type;
    private Double discount;
    private LocalDate endDate;
}
