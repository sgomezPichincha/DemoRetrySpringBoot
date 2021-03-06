package com.nantaaditya.circuitbreaker.services;

import com.nantaaditya.circuitbreaker.model.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientDemoServiceImpl implements ClientDemoService {
    private final RestTemplate restTemplate;

    @Override
    public ResponseDto getDataFromExternalService(Long id) {

        final String URL = "http://localhost:8088/api/persona/{id}";
        ResponseEntity<ResponseDto> response = restTemplate.getForEntity(URL, ResponseDto.class, id);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public ResponseDto getDataFromExternalService(Long id, Long time) {

        final String URL = "http://localhost:8088/api/persona/{time}/{id}";
        ResponseEntity<ResponseDto> response = restTemplate.getForEntity(URL, ResponseDto.class, time, id);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public ResponseDto getDataFromExternalService(Long id, String error) {

        final String URL = "http://localhost:8088/api/persona/error/{1}/{2}";
        ResponseEntity<ResponseDto> response = restTemplate.getForEntity(URL, ResponseDto.class, error, id);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }
}
