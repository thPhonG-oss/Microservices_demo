package com.demo.order_service.service;

import com.demo.order_service.DTOs.request.OrderItemRequest;
import com.demo.order_service.DTOs.request.OrderRequest;
import com.demo.order_service.DTOs.request.ProductStockRequest;
import com.demo.order_service.DTOs.request.UpdateStatusRequest;
import com.demo.order_service.DTOs.response.OrderResponse;
import com.demo.order_service.DTOs.response.OrderStatusResponse;
import com.demo.order_service.DTOs.response.ProductResponse;
import com.demo.order_service.client.ProductClient;
import com.demo.order_service.exception.InsufficientStockException;
import com.demo.order_service.exception.InvalidOrderOperationException;
import com.demo.order_service.exception.OrderNotFoundException;
import com.demo.order_service.exception.ProductNotFoundException;
import com.demo.order_service.mapper.OrderMapper;
import com.demo.order_service.model.Order;
import com.demo.order_service.model.OrderItem;
import com.demo.order_service.model.OrderStatus;
import com.demo.order_service.repository.OrderItemRepository;
import com.demo.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    OrderMapper orderMapper;

    ProductClient productClient;


    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .customerPhone(request.getCustomerPhone())
                .shippingAddress(request.getShippingAddress())
                .totalAmount(0.0)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isCancelled(false)
                .isDeleted(false)
                .build();

        List<OrderItem> orderItems = buildOrderItems(request.getItems(), order);

        order.setOrderItems(orderItems);
        order.setTotalAmount(calculateTotalAmount(order.getOrderItems()));

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findByIdAndIsCancelledFalseAndIsDeletedFalse(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderMapper.toOrderResponse(order);
    }
    @Override
    public OrderResponse getOrderByOrderNumber(String orderNumber) {

        Order order = orderRepository.findByOrderNumberAndIsCancelledFalseAndIsDeletedFalse(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException("order number", orderNumber));

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAllByIsCancelledFalseAndIsDeletedFalse();
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public OrderStatusResponse updateOrderStatus(Long id, UpdateStatusRequest newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (Boolean.TRUE.equals(order.getIsCancelled()))
            throw new InvalidOrderOperationException("Order already cancelled. Cannot update status.");
        if (Boolean.TRUE.equals(order.getIsDeleted()))
            throw new InvalidOrderOperationException("Order already deleted. Cannot update status.");
        order.setStatus(newStatus.getStatus());
        order.setUpdatedAt(LocalDateTime.now());

        Order updated = orderRepository.save(order);
        return orderMapper.toOrderStatusResponse(updated);
    }

    @Override
    public OrderStatusResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (Boolean.TRUE.equals(order.getIsCancelled()))
            throw new InvalidOrderOperationException("Order already cancelled.");

        if (Boolean.TRUE.equals(order.getIsDeleted()))
            throw new InvalidOrderOperationException("Order already deleted.");

        order.setIsCancelled(true);
        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());

        Order updated = orderRepository.save(order);
        return orderMapper.toOrderStatusResponse(updated);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (Boolean.TRUE.equals(order.getIsDeleted()))
            throw new InvalidOrderOperationException("Order already deleted.");
        order.setIsDeleted(true);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }

    private List<OrderItem> buildOrderItems(List<OrderItemRequest> orderItemRequests, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        ProductResponse product;

        for(OrderItemRequest item : orderItemRequests) {
            try {

                product = productClient.getProductById(item.getProductId());
                log.info("Product id: {}", product.getId());
                log.info("Product name: {}", product.getName());
            }
            catch(Exception e){
                throw new ProductNotFoundException("Product not found");
            }
            if(product.getStock() < item.getQuantity()){
                throw new InsufficientStockException(
                        product.getId(),
                        product.getName(),
                        item.getQuantity(),
                        product.getStock()
                );
            }

            try {
                productClient.reduceStock(
                        product.getId(),
                        new ProductStockRequest(item.getQuantity())
                );
            } catch (Exception e) {
                // Nếu trừ kho thất bại (do hết hàng giữa chừng), throw lỗi để rollback
                throw new InsufficientStockException(
                        product.getId(),
                        product.getName(),
                        item.getQuantity(),
                        product.getStock() // Stock cũ
                );
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(product.getId())
                    .productName(product.getName())
                    .price(product.getPrice())
                    .quantity(item.getQuantity())
                    .subtotal(product.getPrice() * item.getQuantity())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private Double calculateTotalAmount(List<OrderItem> orderItems){
        Double totalAmount = 0.0;
        for(OrderItem orderItem : orderItems){
            totalAmount += orderItem.getSubtotal();
        }
        return totalAmount;
    }

}
