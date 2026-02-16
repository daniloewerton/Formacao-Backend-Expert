package br.com.ewerton.orderserviceapi.services.impl;

import br.com.ewerton.orderserviceapi.entities.Order;
import br.com.ewerton.orderserviceapi.mapper.OrderMapper;
import br.com.ewerton.orderserviceapi.repositories.OrderRepository;
import br.com.ewerton.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public Order findById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found. Id: " + id + ", Type: " + Order.class.getSimpleName()
                        ));
    }

    @Override
    public void save(CreateOrderRequest request) {
        repository.save(mapper.fromRequest(request));
    }

    @Override
    public OrderResponse update(final Long id, UpdateOrderRequest request) {
        Order entity = findById(id);
        entity = mapper.fromRequest(entity, request);

        if (entity.getStatus().equals(OrderStatusEnum.CLOSED)) {
            entity.setClosedAt(now());
        }

        return mapper.fromEntity(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        final Order entity = findById(id);
        repository.delete(entity);
    }
}
