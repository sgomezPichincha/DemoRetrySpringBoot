package com.pichincha.demo.retriable.controllers;

import com.pichincha.demo.retriable.dto.ResponseDto;
import com.pichincha.demo.retriable.services.RetryDemoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/retry/annotation")
@AllArgsConstructor
public class RetryWhitAnnotationController {

    private RetryDemoService service;

    @GetMapping(value = "/persona/{id}")
    public ResponseEntity getPerson(@PathVariable("id") Long id) {
        HttpStatus status = HttpStatus.OK;
        ResponseDto response = service.getData(id);
        if (response.getStatus() != null) {
            status = HttpStatus.NOT_FOUND;
        }

        return ResponseEntity.status(status).body(response);
    }

}
