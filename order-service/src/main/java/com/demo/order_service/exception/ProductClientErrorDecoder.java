package com.demo.order_service.exception;

import feign.Response;
import feign.codec.ErrorDecoder;


public class ProductClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 404:
                return new ProductNotFoundException("Product not found");
            case 503:
                return new Exception("Product Service is unavailable");
            default:
                return new Exception("Error calling Product Service");
        }
    }
}
