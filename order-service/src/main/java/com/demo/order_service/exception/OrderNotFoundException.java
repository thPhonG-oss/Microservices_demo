package com.demo.order_service.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
    public OrderNotFoundException(Long orderId) {
        super("Order not found with ID: " + orderId);
    }

    public OrderNotFoundException(String field, String value) {
        super(String.format("Order not found with %s: %s", field, value));
    }
}
