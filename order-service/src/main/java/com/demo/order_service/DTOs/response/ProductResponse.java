package com.demo.order_service.DTOs.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;
    String name;
    String description;
    Double price;
    Integer stock;
    String category;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
