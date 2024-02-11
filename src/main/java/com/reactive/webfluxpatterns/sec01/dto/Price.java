package com.reactive.webfluxpatterns.sec01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor(staticName = "create")
public class Price {

    private Integer listPrice;
    private Double discount;
    private Double discountedPrice;
    private Double amountSaved;
    private LocalDate endDate;

}
