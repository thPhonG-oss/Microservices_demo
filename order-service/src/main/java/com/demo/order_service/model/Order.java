package com.demo.order_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @NotBlank(message = "Customer name is required")
    @Column(name = "customer_name", nullable = false)
    String customerName;

    @Pattern(regexp = "^[0-9]{10,11}", message = "Invalid phone number")
    @Column(name = "customer_phone")
    String customerPhone;

    @Column(name = "shipping_address", nullable = false)
    String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    OrderStatus status;

    @Column(name = "total_amount", nullable = false)
    Double totalAmount;

    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
