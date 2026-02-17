package br.com.ewerton.orderserviceapi.services.impl;

import br.com.ewerton.orderserviceapi.clients.UserServiceFeignClient;
import br.com.ewerton.orderserviceapi.entities.Order;
import br.com.ewerton.orderserviceapi.mapper.OrderMapper;
import br.com.ewerton.orderserviceapi.repositories.OrderRepository;
import br.com.ewerton.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import models.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public Order findById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found. Id: " + id + ", Type: " + Order.class.getSimpleName()
                        ));
    }

    @Override
    public void save(CreateOrderRequest request) {
        validateUserId(request.requesterId());
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

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Order> findAllPaginated(Integer page,
                                        Integer linesPerPage,
                                        String direction,
                                        String orderBy) {
        PageRequest pageRequest = PageRequest.of(
                page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    void validateUserId(final String userId) {
        final UserResponse response = userServiceFeignClient.findById(userId).getBody();
        log.info("User found: {}", response);
    }
}
