package com.nantaaditya.circuitbreaker.services;

import com.nantaaditya.circuitbreaker.model.ResponseDto;

public interface ClientDemoService {
    public ResponseDto getDataFromExternalService(Long id) ;
}
