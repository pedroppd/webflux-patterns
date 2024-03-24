package com.reactive.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class InventoryResponse {
    private UUID inventoryId;
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private Status status;
}
