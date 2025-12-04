package com.demo.order_service.service;

import com.demo.order_service.DTOs.request.OrderRequest;
import com.demo.order_service.DTOs.request.UpdateStatusRequest;
import com.demo.order_service.DTOs.response.OrderResponse;
import com.demo.order_service.DTOs.response.OrderStatusResponse;
import com.demo.order_service.model.OrderStatus;

import java.util.List;

public interface OrderService {
    /**
     * Tạo đơn hàng mới
     */
    OrderResponse createOrder(OrderRequest request);

    /**
     * Lấy order theo ID
     */
    OrderResponse getOrderById(Long id);

    /**
     * Lấy order theo order number
     */
    OrderResponse getOrderByOrderNumber(String orderNumber);

    /**
     * Lấy tất cả orders
     */
    List<OrderResponse> getAllOrders();

    /**
     * Cập nhật trạng thái order
     */
    OrderStatusResponse updateOrderStatus(Long id, UpdateStatusRequest newStatus);

    /**
     * Hủy đơn hàng
     */
    OrderStatusResponse cancelOrder(Long id);

    /**
     * Xóa order (soft delete hoặc hard delete)
     */
    void deleteOrder(Long id);
}
