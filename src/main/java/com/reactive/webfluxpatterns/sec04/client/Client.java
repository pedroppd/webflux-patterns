package com.reactive.webfluxpatterns.sec04.client;

public interface Client<T, K> {
    T get(K value);
    T post(K value);
}
