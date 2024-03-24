package com.reactive.webfluxpatterns.sec04.dto;

import lombok.*;

@Data
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Product {

    private Integer id;
    private String category;
    private String description;
    private Integer price;
}
