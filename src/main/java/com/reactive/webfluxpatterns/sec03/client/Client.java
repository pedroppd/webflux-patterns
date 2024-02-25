package com.reactive.webfluxpatterns.sec03.client;

public interface Client<T, K> {
    T get(K value);
    T post(K value);
}
