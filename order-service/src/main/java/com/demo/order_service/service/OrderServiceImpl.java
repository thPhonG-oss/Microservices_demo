package com.demo.order_service.service;

import com.demo.order_service.DTOs.response.OrderItemResponseDTO;
import com.demo.order_service.mapper.OrderMapper;
import com.demo.order_service.repository.OrderItemRepository;
import com.demo.order_service.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    OrderMapper orderMapper;

    @Override

}
