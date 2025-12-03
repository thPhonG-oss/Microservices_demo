package com.demo.product_service.DTOs;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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

    /**
     * Indicator if product is available
     */
    public boolean isAvailable() {
        return stock != null && stock > 0;
    }
}
