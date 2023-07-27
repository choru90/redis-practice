package com.choru.stock.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<RestApiErrorResponse> handleResponseStatusException(final RestApiException ex) {

        RestApiErrorResponse restApiErrorResponse = new RestApiErrorResponse(ex.getCode(), ex.getMessage());

        return new ResponseEntity<>(restApiErrorResponse, ex.getCode().getStatus());
    }
}
