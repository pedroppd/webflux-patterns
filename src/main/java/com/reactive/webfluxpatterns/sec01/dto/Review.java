package com.reactive.webfluxpatterns.sec01.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Review {

    private Integer id;
    private String user;
    private Integer rating;
    private String comment;


}
