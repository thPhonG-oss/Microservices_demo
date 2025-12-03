package com.demo.order_service.exception;

public class InsufficientStockException extends RuntimeException {
    private final String productId;
    private final String productName;
    private final Integer requestedQuantity;
    private final Integer availableStock;
    public InsufficientStockException(
            String productId,
            String productName,
            Integer requestedQuantity,
            Integer availableStock
    ) {
        super(String.format(
                "Product '%s' has insufficient stock. Requested: %d, Available: %d",
                productName, requestedQuantity, availableStock
        ));
        this.productId = productId;
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
        this.availableStock = availableStock;
    }

    // Getters
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Integer getRequestedQuantity() { return requestedQuantity; }
    public Integer getAvailableStock() { return availableStock; }
}
