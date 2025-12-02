package com.demo.product_service.service;

import com.demo.product_service.DTOs.ProductRequest;
import com.demo.product_service.DTOs.ProductResponse;

import java.util.List;

public interface ProductService {

    /**
     * Create a new product
     */
    ProductResponse createProduct(ProductRequest request);
    /**
     * Get product by ID
     */
    ProductResponse getProductById(String id);

    /**
     * Get all products
     */
    List<ProductResponse> getAllProducts();

    /**
     * Update existing product
     */
    ProductResponse updateProduct(String id, ProductRequest request);

    /**
     * Delete product by ID
     */
    void deleteProduct(String id);

    /**
     * Get products by category
     */
    List<ProductResponse> getProductsByCategory(String category);

    /**
     * Search products by name
     */
    List<ProductResponse> searchProductsByName(String name);

    /**
     * Get products by price range
     */
    List<ProductResponse> getProductsByPriceRange(Double minPrice, Double maxPrice);
}
