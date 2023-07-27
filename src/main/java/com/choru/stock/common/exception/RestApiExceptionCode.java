package com.choru.stock.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RestApiExceptionCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "ERROR-400");

    private HttpStatus status;
    private String code;

}
