package com.demo.product_service.service;

import com.demo.product_service.DTOs.ProductRequest;
import com.demo.product_service.DTOs.ProductResponse;
import com.demo.product_service.mapper.ProductMapper;
import com.demo.product_service.model.Product;
import com.demo.product_service.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating product with name " + request.getName());
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // Save to database
        Product savedProduct = productRepository.save(product);

        log.info("Product created successfully with ID: {}", savedProduct.getId());

        // Convert Entity to Response DTO
//        return mapToResponse(savedProduct);
        return null;
    }


    @Override
    public ProductResponse getProductById(String id) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return List.of();
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest request) {
        return null;
    }

    @Override
    public void deleteProduct(String id) {

    }

    @Override
    public List<ProductResponse> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<ProductResponse> searchProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<ProductResponse> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return List.of();
    }
}
