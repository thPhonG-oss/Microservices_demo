package com.demo.product_service.repository;

import com.demo.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    /**
     * Find products by category
     * Method naming convention: findBy + FieldName
     */
    List<Product> findByCategory(String category);

    /**
     * Find products by name (case-insensitive, partial match)
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Find products by price range
     */
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Find products with stock greater than specified value
     */
    List<Product> findByStockGreaterThan(Integer stock);

    /**
     * Custom query using @Query annotation
     * Find products with low stock (< threshold)
     */
    @Query("{ 'stock' : { $lt: ?0 } }")
    List<Product> findLowStockProducts(Integer threshold);

    /**
     * Check if product exists by name
     */
    boolean existsByName(String name);
}
