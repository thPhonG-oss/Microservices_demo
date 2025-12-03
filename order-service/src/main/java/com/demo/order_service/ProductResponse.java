package com.demo.order_service;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
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
