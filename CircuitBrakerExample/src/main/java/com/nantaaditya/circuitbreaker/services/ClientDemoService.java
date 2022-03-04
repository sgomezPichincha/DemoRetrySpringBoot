package com.nantaaditya.circuitbreaker.services;

import com.nantaaditya.circuitbreaker.model.ResponseDto;

public interface ClientDemoService {
    public ResponseDto getDataFromExternalService(Long id);

    public ResponseDto getDataFromExternalService(Long id, Long time);

    public ResponseDto getDataFromExternalService(Long id, String error);
}
