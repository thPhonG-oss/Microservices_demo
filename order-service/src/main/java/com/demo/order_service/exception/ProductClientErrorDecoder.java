package com.demo.order_service.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

import javax.naming.ServiceUnavailableException;

public class ProductClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 404:
                return new ProductNotFoundException("Product not found");
                break;
            case 503:
                return new ServiceUnavailableException("Service unavailable");
            default:
                return new Exception("Error calling Product Service");
        }
    }
}
