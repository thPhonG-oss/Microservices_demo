package com.demo.order_service.DTOs.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    String customerName;
    String customerPhone;
    String customerEmail;
    String shippingAddress;
    List<OrderItemRequest> items = new ArrayList<>();
}
