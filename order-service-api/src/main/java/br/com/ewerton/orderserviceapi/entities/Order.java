package br.com.ewerton.orderserviceapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.enums.OrderStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static models.enums.OrderStatusEnum.OPEN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String requestId;

    @Column(nullable = false, length = 45)
    private String customerId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status = OPEN;

    private final LocalDateTime createdAt = now();

    private LocalDateTime closedAt;
}
