package com.demo.order_service.mapper;

import com.demo.order_service.DTOs.response.OrderItemResponse;
import com.demo.order_service.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
