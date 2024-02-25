package com.reactive.webfluxpatterns.sec03.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderResponse {

    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Status status;
    private Address shippingAddress;
    private String expectedDelivery;

}
