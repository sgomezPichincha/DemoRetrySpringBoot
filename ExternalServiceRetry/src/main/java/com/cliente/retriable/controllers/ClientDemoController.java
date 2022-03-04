package com.cliente.retriable.controllers;

import com.cliente.retriable.dto.PersonDto;
import com.cliente.retriable.dto.ResponseMessage;
import com.cliente.retriable.exception.ServiceFiledException;
import com.cliente.retriable.services.ClientDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.time.Duration;
import java.util.Random;

@RestController
@RequestMapping("/api/persona")
public class ClientDemoController {
    @Autowired
    private ClientDemoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable("id") Long id) {
        HttpStatus status = HttpStatus.OK;
        int random = (int) (Math.random() * 100);

        System.out.println(random);
        if (random > 50) {
            throw new ServiceFiledException("ERROR AL CONSULTAR LA PERSONA");
        }
        PersonDto person = service.getData(id);
        if (person == null) {
            person = PersonDto.builder().status(ResponseMessage.builder()
                    .code(HttpStatus.NOT_FOUND.toString())
                    .message("No se encontró persona con id " + id)
                    .build()).build();
        }
        return ResponseEntity.status(status).body(person);
    }

    @GetMapping(value = "/error/{error}/{id}")
    public ResponseEntity<PersonDto> getPersonWithError(@PathVariable("id") Long id, @PathVariable(value = "error") String error) {
        HttpStatus status = HttpStatus.OK;

        if ("si".equals(error)) {
            throw new ServiceFiledException("ERROR AL CONSULTAR LA PERSONA");
        }
        PersonDto person = service.getData(id);
        if (person == null) {
            person = PersonDto.builder().status(ResponseMessage.builder()
                    .code(HttpStatus.NOT_FOUND.toString())
                    .message("No se encontró persona con id " + id)
                    .build()).build();
        }
        return ResponseEntity.status(status).body(person);
    }


    @GetMapping(value = "/{time}/{id}")
    public ResponseEntity<PersonDto> getPersonTimeOut(@PathVariable Long time, @PathVariable Long id) {
        HttpStatus status = HttpStatus.OK;
        try {
            Thread.sleep(Duration.ofSeconds(time * 1000).getSeconds());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PersonDto person = service.getData(id);
        if (person == null) {
            person = PersonDto.builder().status(ResponseMessage.builder()
                    .code(HttpStatus.NOT_FOUND.toString())
                    .message("No se encontró persona con id " + id)
                    .build()).build();
        }
        return ResponseEntity.status(status).body(person);
    }
}
