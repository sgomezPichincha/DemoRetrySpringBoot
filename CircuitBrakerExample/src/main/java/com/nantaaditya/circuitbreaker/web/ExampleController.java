package com.nantaaditya.circuitbreaker.web;

import com.nantaaditya.circuitbreaker.model.Response;
import com.nantaaditya.circuitbreaker.model.ResponseDto;
import com.nantaaditya.circuitbreaker.services.ClientDemoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @see //github.com/nantaaditya/circuit-breaker-example
 */
@RestController
@RequestMapping("/example/")
@Slf4j
@RequiredArgsConstructor
public class ExampleController {

    private static final String RESILIENCE4J_INSTANCE_NAME = "example";
    private static final String FALLBACK_METHOD = "fallbackCustom";

    private final ClientDemoService service;

    @GetMapping(
            value = "/data/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public Response errorCustom(@PathVariable Long id) throws Exception {
        ResponseDto response = service.getDataFromExternalService(id);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(response)
                .build();
    }

    public Response fallbackCustom(Exception ex) {
        log.info("Esto es un error fallback custom {}", ex.getMessage());

        return toResponse(HttpStatus.INTERNAL_SERVER_ERROR, Boolean.FALSE);

    }

    private Response<Boolean> toResponse(HttpStatus httpStatus, Boolean result) {
        return Response.<Boolean>builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .data(result)
                .build();
    }
}
