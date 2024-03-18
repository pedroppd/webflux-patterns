package com.reactive.webfluxpatterns.sec03.service;

import com.reactive.webfluxpatterns.sec03.client.ProductClient;
import com.reactive.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import com.reactive.webfluxpatterns.sec03.dto.OrderRequest;
import com.reactive.webfluxpatterns.sec03.dto.OrderResponse;
import com.reactive.webfluxpatterns.sec03.dto.Status;
import com.reactive.webfluxpatterns.sec03.util.OrchestrationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.reactive.webfluxpatterns.sec03.dto.Product;

@Service
public class OrchestratorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderFulfillmentService fulfillmentService;

    @Autowired
    private OrderCancellationService cancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono) {
        return mono
                .map(OrchestrationRequestContext::new)
                .flatMap(this::setProductPriceCtx)
                .doOnNext(OrchestrationFactory::buildRequestContext)
                .flatMap(fulfillmentService::placeOrder)
                .doOnNext(this::doOrderPostProcessing)
                //.doOnNext(DebugUtil::print) // just for debugging
                .map(this::toOrderResponse);
    }

    private Mono<OrchestrationRequestContext> setProductPriceCtx(OrchestrationRequestContext ctx) {
        return this.productClient.get(ctx.getOrderRequest().getProductId())
                .map(Product::getPrice)
                .doOnNext(ctx::setProductPrice)
                .map(i -> ctx);
    }

    private void doOrderPostProcessing(OrchestrationRequestContext ctx) {
        if (Status.FAILED.equals(ctx.getStatus())) this.cancellationService.cancelOrder(ctx);
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext ctx) {
        var isSuccess = Status.SUCCESS.equals(ctx.getStatus());
        var address = isSuccess ? ctx.getShippingResponse().getAddress() : null;
        var deliveryDate = isSuccess ? ctx.getShippingResponse().getExpectedDelivery() : null;
        return OrderResponse.create(
                ctx.getOrderRequest().getUserId(),
                ctx.getOrderRequest().getProductId(),
                ctx.getOrderId(),
                ctx.getStatus(),
                address,
                deliveryDate
        );
    }

}
