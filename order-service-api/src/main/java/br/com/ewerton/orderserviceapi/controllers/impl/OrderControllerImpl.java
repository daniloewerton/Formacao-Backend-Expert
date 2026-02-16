package br.com.ewerton.orderserviceapi.controllers.impl;

import br.com.ewerton.orderserviceapi.controllers.OrderController;
import br.com.ewerton.orderserviceapi.mapper.OrderMapper;
import br.com.ewerton.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService services;
    private final OrderMapper mapper;

    @Override
    public ResponseEntity<OrderResponse> findById(Long id) {
        return ResponseEntity.ok().body(
                mapper.fromEntity(services.findById(id))
        );
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok().body(
                mapper.fromEntities(
                        services.findAll()
                )
        );
    }

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        services.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok(services.update(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteById(final Long id) {
        services.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
