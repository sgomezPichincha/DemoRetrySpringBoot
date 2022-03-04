package com.nantaaditya.circuitbreaker.web;

import com.nantaaditya.circuitbreaker.model.Response;
import com.nantaaditya.circuitbreaker.model.ResponseDto;
import com.nantaaditya.circuitbreaker.services.ClientDemoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * @see //github.com/nantaaditya/circuit-breaker-example
 */
@RestController
@RequestMapping("/example")
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
    public Response errorCustom(@PathVariable Long id, @RequestParam(value = "error", defaultValue = "no") String error) throws Exception {
        ResponseDto response = service.getDataFromExternalService(id, error);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(response)
                .build();
    }

    int count = 0;

    @GetMapping(
            value = "/data/withRetry/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME)
    @Retry(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public Response errorCustomRetry(@PathVariable Long id) /*throws Exception*/ {

        log.info("Intentando consultar {} ", ++count);
        ResponseDto response = service.getDataFromExternalService(id);
        count = 0;
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .data(response)

                .build();
    }

    @GetMapping(
            value = "/data/{id}/{time}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = "fallbackCustomT")
    @TimeLimiter(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = "fallbackCustomT")
    public CompletableFuture<Response<ResponseDto>> timeOutCustom(@PathVariable Long id, @PathVariable Long time) /*throws Exception */ {
        return CompletableFuture.supplyAsync(() ->
                toResponse(HttpStatus.OK, service.getDataFromExternalService(id, time)));
    }

    public Response<ResponseDto> fallbackCustom(Exception ex) {
        count = 0;
        log.info("Esto es un error fallback custom {}", ex.getMessage());
        return toResponse(HttpStatus.INTERNAL_SERVER_ERROR, new ResponseDto());
    }

    public CompletableFuture<Response<ResponseDto>> fallbackCustomT(Exception ex) {
        log.info("Esto es un error fallback para el timeout {}", ex.getMessage());
        return CompletableFuture.completedFuture(toResponse(HttpStatus.INTERNAL_SERVER_ERROR, new ResponseDto()));
    }

    private Response<ResponseDto> toOkResponse(ResponseDto dto) {
        return toResponse(HttpStatus.OK, dto);
    }

    private Response<ResponseDto> toResponse(HttpStatus httpStatus, ResponseDto result) {
        return Response.<ResponseDto>builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .data(result)
                .build();
    }


}
