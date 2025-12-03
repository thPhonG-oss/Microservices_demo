package com.demo.product_service.mapper;

import com.demo.product_service.DTOs.ProductResponse;
import com.demo.product_service.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);
}
