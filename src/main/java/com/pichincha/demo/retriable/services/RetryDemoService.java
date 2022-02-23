package com.pichincha.demo.retriable.services;

import com.pichincha.demo.retriable.dto.ResponseDto;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

/**
 * Implementación de Spring Boot Retry con anotación @Retryable
 * y usando un método recover con @Recover
 */
public interface RetryDemoService {

    final int MAX_ATTEMPTS = 4; //intentos máximos
    final int BACK_OFF = 1000; //intervalo de tiempo para el reintento (milisegundos)

    @Retryable(value = {RuntimeException.class}, maxAttempts = MAX_ATTEMPTS, backoff = @Backoff(BACK_OFF))
    public ResponseDto getData(Long id);

}
