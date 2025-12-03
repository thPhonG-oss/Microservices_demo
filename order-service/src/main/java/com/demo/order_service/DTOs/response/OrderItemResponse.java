package com.demo.order_service.DTOs.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {
    Long id;
    String productId;
    String productName;
    Double price;
    Integer quantity;
    Double subtotal;
    LocalDateTime createdAt;
}
