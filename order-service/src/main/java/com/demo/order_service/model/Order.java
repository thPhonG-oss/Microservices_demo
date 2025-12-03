package com.demo.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_number", nullable = false, unique = true)
    String orderNumber;

    @Column(name = "customer_name", nullable = false)
    String customerName;

    @Column(name = "customer_phone")
    String customerPhone;

    @Column(name = "shipping_address", nullable = false)
    String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    OrderStatus status;

    @Column(name = "total_amount", nullable = false)
    Double totalAmount;


}
