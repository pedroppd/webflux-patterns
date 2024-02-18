package com.reactive.webfluxpatterns.sec02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor(staticName = "create")
public class FrontierRequestDTO {
    private String from;
    private String to;
}
