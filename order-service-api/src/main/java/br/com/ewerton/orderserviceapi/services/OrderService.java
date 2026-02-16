package br.com.ewerton.orderserviceapi.services;

import br.com.ewerton.orderserviceapi.entities.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

import java.util.List;

public interface OrderService {

    Order findById(final Long id);

    void save(CreateOrderRequest request);

    OrderResponse update(Long id, UpdateOrderRequest request);

    void deleteById(final Long id);

    List<Order> findAll();
}
