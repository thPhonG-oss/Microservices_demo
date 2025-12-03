package com.demo.order_service.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
    public ServiceUnavailableException(String serviceName, Throwable cause) {
        super(serviceName, cause);
    }
}

