package com.reactive.webfluxpatterns.sec01.services;

import com.reactive.webfluxpatterns.sec01.client.ProductClient;
import com.reactive.webfluxpatterns.sec01.client.PromotionClient;
import com.reactive.webfluxpatterns.sec01.client.ReviewClient;
import com.reactive.webfluxpatterns.sec01.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregatorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PromotionClient promotionClient;

    @Autowired
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
        return Mono.zip(
                productClient.get(id),
                promotionClient.get(id),
                reviewClient.get(id)
        ).map(p -> this.toDTO(p.getT1(), p.getT2(), p.getT3()));
    }

    private ProductAggregate toDTO(ProductResponse product, PromotionResponse promotion, List<Review> reviews) {
        var ammountSaved = (promotion.getDiscount() / 100) * product.getPrice();
        var discountedPrice = product.getPrice() - ammountSaved;
        var price = Price.create(product.getPrice(), promotion.getDiscount(), discountedPrice, ammountSaved, promotion.getEndDate());
        return ProductAggregate.create(product.getId(),
                product.getCategory(),
                product.getDescription(),
                price, reviews);
    }
}
