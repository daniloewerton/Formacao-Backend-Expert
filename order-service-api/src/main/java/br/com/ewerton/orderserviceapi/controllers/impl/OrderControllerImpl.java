package br.com.ewerton.orderserviceapi.controllers.impl;

import br.com.ewerton.orderserviceapi.controllers.OrderController;
import br.com.ewerton.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService services;

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        services.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
