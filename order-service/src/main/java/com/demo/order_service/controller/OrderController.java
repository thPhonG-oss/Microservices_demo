package com.demo.order_service.controller;

import com.demo.order_service.DTOs.request.OrderRequest;
import com.demo.order_service.DTOs.request.UpdateStatusRequest;
import com.demo.order_service.DTOs.response.OrderResponse;
import com.demo.order_service.DTOs.response.OrderStatusResponse;
import com.demo.order_service.model.OrderStatus;
import com.demo.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }
    // ------------------- GET ORDER BY ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // ------------------- GET ORDER BY NUMBER -------------------
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderResponse> getByOrderNumber(@PathVariable String orderNumber) {
        return ResponseEntity.ok(orderService.getOrderByOrderNumber(orderNumber));
    }

    // ------------------- GET ALL ORDERS -------------------
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ------------------- UPDATE STATUS -------------------
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderStatusResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest status
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    // ------------------- CANCEL ORDER -------------------
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderStatusResponse> cancelOrder(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    // ------------------- DELETE (SOFT DELETE) -------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Xóa đơn hàng thành công");
    }

}
