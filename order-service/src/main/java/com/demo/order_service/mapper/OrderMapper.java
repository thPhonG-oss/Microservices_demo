package com.demo.order_service.mapper;

import com.demo.order_service.DTOs.response.OrderResponse;
import com.demo.order_service.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "items", source = "orderItems")
    OrderResponse toOrderResponse(Order order);
}
