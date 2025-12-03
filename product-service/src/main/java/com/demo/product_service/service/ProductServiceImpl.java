package com.demo.product_service.service;

import com.demo.product_service.DTOs.ProductRequest;
import com.demo.product_service.DTOs.ProductResponse;
import com.demo.product_service.exception.ProductNotFoundException;
import com.demo.product_service.mapper.ProductMapper;
import com.demo.product_service.model.Product;
import com.demo.product_service.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Port;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    @Transactional
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
        return productMapper.toProductResponse(savedProduct);
    }


    @Override
    public ProductResponse getProductById(String id) {
        log.info("Retrieving product with ID: " + id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return productMapper.toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();


        return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest request) {
        log.info("Updating product with ID: " + id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID: " + id + " not found"));

        //update fields
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setUpdatedAt(LocalDateTime.now());
        Product updatedProduct = productRepository.save(product);

        log.info("Product updated successfully with ID: " + updatedProduct.getId());

        return productMapper.toProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {
        log.info("Deleting product with ID: " + id);
        if(!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID: " + id + " not found");
        }
        productRepository.deleteById(id);

        log.info("Product deleted successfully with ID: " + id);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(String category) {
        log.info("Retrieving products by category: " + category);
        List<Product> products = productRepository.findByCategory(category);

        return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProductsByName(String name) {
        log.info("Retrieving products by name " + name);
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        log.info("Fetching products by price range " + minPrice + " - " + maxPrice);

        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }
}
