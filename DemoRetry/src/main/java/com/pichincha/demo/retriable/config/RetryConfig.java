package com.pichincha.demo.retriable.config;

import com.pichincha.demo.retriable.exceptions.RetryDemoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@Slf4j
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        final int MAX_ATTEMPTS = 4; //intentos máximos
        final int BACK_OFF = 2000; //intervalo de tiempo para el reintento (milisegundos)
        return RetryTemplate.builder()
                .maxAttempts(MAX_ATTEMPTS)
                .fixedBackoff(BACK_OFF)
                .retryOn(RuntimeException.class)
                .withListener(new ApiRetryListener())
                .build();
    }

}