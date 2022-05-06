package com.cliente.retriable.exception.error;

import com.cliente.retriable.exception.ServiceFiledException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class Advisor {

    @ExceptionHandler(ServiceFiledException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerServiceFiledException(ServiceFiledException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.put("message", ex.getMessage());
        return response;
    }
}
