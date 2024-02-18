package com.reactive.webfluxpatterns.sec02.client;

public interface Client<T, K> {
    T get(K value);
    T post(K value);
}
