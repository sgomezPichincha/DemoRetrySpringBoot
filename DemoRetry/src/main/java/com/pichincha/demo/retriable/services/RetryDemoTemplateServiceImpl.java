package com.pichincha.demo.retriable.services;

import com.pichincha.demo.retriable.dto.ResponseDto;
import com.pichincha.demo.retriable.exceptions.RetryDemoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetryDemoTemplateServiceImpl implements RetryDemoTemplateService {

    private final RetryTemplate retryTemplate;

    private final RestTemplate restTemplate;

    @Value("${service.demo}")
    private String urlService;

    private int attemps = 0;

    @Override
    public ResponseDto getDataDemo(Long id) {
        return retryTemplate.execute(new RetryCallback<ResponseDto, RuntimeException>() {
            @Override
            public ResponseDto doWithRetry(RetryContext context) throws RuntimeException {
                log.info("Intentando consultar {}", ++attemps);
                ResponseEntity<ResponseDto> response;
                try {
                    response = restTemplate.getForEntity(urlService, ResponseDto.class, id);
                } catch (Exception ex) {
                    throw new RetryDemoException("No es posible la comunicación con el servicio " + urlService);
                }
                attemps = 0;
                return response.getBody();
            }

        }/*, new RecoveryCallback<ResponseDto>() {
            @Override
            public ResponseDto recover(RetryContext context) {
                return ResponseDto.builder().build();
            }
        }*/);
    }
}
