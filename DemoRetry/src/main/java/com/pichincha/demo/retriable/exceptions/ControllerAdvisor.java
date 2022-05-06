package com.pichincha.demo.retriable.exceptions;

import com.pichincha.demo.retriable.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(RetryDemoException.class)
    public ResponseEntity handlerRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorDto
                .builder()
                .message(ex.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .time(LocalDateTime.now())
                .build());
    }
}
