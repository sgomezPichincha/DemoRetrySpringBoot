package com.pichincha.demo.retriable.exceptions;

public class RetryDemoException extends RuntimeException{
    public RetryDemoException(String message) {
        super(message);
    }
}
