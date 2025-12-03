package com.demo.order_service.client;

import com.demo.order_service.DTOs.request.ProductStockRequest;
import com.demo.order_service.DTOs.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "product-service",
        path = "/api/products"
)
public interface ProductClient {

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable String id);

    @GetMapping
    List<ProductResponse> getProducts();

    @PutMapping("/{id}/reduce-stock")
    ProductResponse reduceStock(@PathVariable("id") String id, @RequestBody ProductStockRequest request);
}
