package com.demo.order_service.client;

import com.demo.order_service.DTOs.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "product-service",
        path = "/api/products"
)
public interface ProductClient {

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable Integer id);

    @GetMapping
    List<ProductResponse> getProducts();
}
