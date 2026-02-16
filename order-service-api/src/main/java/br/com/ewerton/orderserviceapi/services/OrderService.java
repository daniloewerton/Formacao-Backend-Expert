package br.com.ewerton.orderserviceapi.services;

import models.requests.CreateOrderRequest;

public interface OrderService {

    void save(CreateOrderRequest request);
}
