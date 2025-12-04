package com.demo.order_service.DTOs.response;

import com.demo.order_service.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatusResponse {
    Long id;
    String orderNumber;
    String customerName;
    String customerPhone;
    String customerEmail;
    String shippingAddress;

    Double totalAmount;

    List<OrderItemResponse> items;

    OrderStatus status;
    Boolean isCancelled;
    Boolean isDeleted;
}
