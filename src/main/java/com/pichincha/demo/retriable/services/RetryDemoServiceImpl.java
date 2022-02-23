package com.pichincha.demo.retriable.services;

import com.pichincha.demo.retriable.dto.ErrorDto;
import com.pichincha.demo.retriable.dto.ResponseDto;
import com.pichincha.demo.retriable.exceptions.RetryDemoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * Implementación de Spring Boot Retry con anotación @Retryable
 * y usando un método recover con @Recover
 */
@Service
@Slf4j
public class RetryDemoServiceImpl implements RetryDemoService {

    private RestTemplate restTemplate;

    @Value("${service.demo}")
    private String urlService;

    public RetryDemoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseDto getData(Long id) {
        log.info("Intentando consultar {}", LocalDateTime.now().getSecond());
        ResponseEntity<ResponseDto> response = restTemplate.getForEntity(urlService, ResponseDto.class, id);
        System.out.println(response.getStatusCode());
        return response.getBody();
    }


    /**
     * Método de recuperación que será llamado por Spring al agotar todos los intentos
     * tendrá que devolver lo mismo que el método original
     *
     * @param t
     * @param s
     * @return
     */
    @Recover
    public ResponseDto retryExampleRecovery(RuntimeException t, String s) {
        log.error("Retry Recovery - " + t.getMessage());
        return ResponseDto
                .builder()
                .status(ErrorDto
                        .builder()
                        .message(t.getMessage())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .time(LocalDateTime.now())
                        .build())
                .build();
    }
}
