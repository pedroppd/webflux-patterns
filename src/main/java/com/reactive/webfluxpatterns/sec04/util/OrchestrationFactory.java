package com.reactive.webfluxpatterns.sec04.util;

import com.reactive.webfluxpatterns.sec04.dto.InventoryRequest;
import com.reactive.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.reactive.webfluxpatterns.sec04.dto.PaymentRequest;
import com.reactive.webfluxpatterns.sec04.dto.ShippingRequest;

public class OrchestrationFactory {

    public static void buildRequestContext(OrchestrationRequestContext ctx){
        buildPaymentRequest(ctx);
        buildInventoryRequest(ctx);
        buildShippingRequest(ctx);
    }

    private static void buildPaymentRequest(OrchestrationRequestContext ctx){
        var paymentRequest = PaymentRequest.create(
                ctx.getOrderRequest().getUserId(),
                ctx.getProductPrice() * ctx.getOrderRequest().getQuantity(),
                ctx.getOrderId()
        );
        ctx.setPaymentRequest(paymentRequest);
    }

    private static void buildInventoryRequest(OrchestrationRequestContext ctx){
        var inventoryRequest = InventoryRequest.create(
                ctx.getOrderId(),
                ctx.getOrderRequest().getProductId(),
                ctx.getOrderRequest().getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(OrchestrationRequestContext ctx){
        var shippingRequest = ShippingRequest.create(
                ctx.getOrderRequest().getQuantity(),
                ctx.getOrderRequest().getUserId(),
                ctx.getOrderId()
        );
        ctx.setShippingRequest(shippingRequest);
    }
}
