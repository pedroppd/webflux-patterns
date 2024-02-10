package com.reactive.webfluxpatterns.sec01.client;

public interface Client<T, K> {

    T get(K value);
}
