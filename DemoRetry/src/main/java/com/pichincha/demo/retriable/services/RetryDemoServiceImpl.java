package com.pichincha.demo.retriable.services;

import com.pichincha.demo.retriable.dto.ErrorDto;
import com.pichincha.demo.retriable.dto.ResponseDto;
import com.pichincha.demo.retriable.exceptions.RetryDemoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * Implementación de Spring Boot Retry con anotación @Retryable
 * y usando un método recover con @Recover
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RetryDemoServiceImpl implements RetryDemoService {

    private final RestTemplate restTemplate;

    @Value("${service.demo}")
    private String urlService;

    private int attemps = 0;

    @Override
    public ResponseDto getData(Long id) {
        log.info("Intentando consultar {}", ++attemps);
        ResponseEntity<ResponseDto> response = null;
        try {
            response = restTemplate.getForEntity(urlService, ResponseDto.class, id);
        } catch (Exception ex) {
            throw new RetryDemoException("No es posible la comunicación con el servicio " + urlService);
        } finally {
            if (response != null)
                log.info("Status {}", response.getStatusCode().toString());
        }
        attemps = 0;
        return response.getBody();
    }


    /**
     * Método de recuperación que será llamado por Spring al agotar todos los intentos
     * tendrá que devolver lo mismo que el método original
     *
     * @param e
     * @return
     */
    @Override
    public ResponseDto retryExampleRecovery(RuntimeException e) {
        log.error("Retry Recovery - {}", e.getLocalizedMessage());
        return ResponseDto
                .builder()
                .status(ErrorDto
                        .builder()
                        .message(e.getMessage())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .time(LocalDateTime.now())
                        .build())
                .build();
    }
}
