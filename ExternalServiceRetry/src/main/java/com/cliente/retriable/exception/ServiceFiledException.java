package com.cliente.retriable.exception;

public class ServiceFiledException extends RuntimeException{
    public ServiceFiledException(String message) {
        super(message);
    }
}
