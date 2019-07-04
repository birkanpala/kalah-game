package com.backbase.kalahgame.exception;

import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

import static java.time.Instant.now;
import static java.util.Arrays.asList;

@Value
class ApiExceptionResponse {

    private Instant timestamp;

    private HttpStatus status;

    private List<String> errors;

    static ApiExceptionResponse of(HttpStatus httpStatus, String... errors) {

        return new ApiExceptionResponse(now(), httpStatus, asList(errors));
    }

    static ApiExceptionResponse of(HttpStatus httpStatus, List<String> errors) {

        return new ApiExceptionResponse(now(), httpStatus, errors);
    }

}