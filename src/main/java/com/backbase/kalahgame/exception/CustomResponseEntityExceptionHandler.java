package com.backbase.kalahgame.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                        WebRequest request) {

        ApiExceptionResponse response = ApiExceptionResponse.of(HttpStatus.NOT_FOUND, ex.getMessage());

        log.error(ex.getMessage(), ex);

        return createResponseEntity(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                       WebRequest request) {

        ApiExceptionResponse response = ApiExceptionResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());

        log.error(ex.getMessage(), ex);

        return createResponseEntity(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public final ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex,
                                                                       WebRequest request) {

        ApiExceptionResponse response = ApiExceptionResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());

        log.error(ex.getMessage(), ex);

        return createResponseEntity(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<String> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ", " + e.getDefaultMessage())
                .collect(Collectors.toList());

        List<String> globalErrors = ex.getBindingResult().getGlobalErrors()
                .stream()
                .map(e -> e.getObjectName() + ", " + e.getDefaultMessage())
                .collect(Collectors.toList());

        List<String> errors = Stream.of(fieldErrors, globalErrors)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        ApiExceptionResponse response = ApiExceptionResponse.of(HttpStatus.BAD_REQUEST, errors);

        log.error(ex.getMessage(), ex);

        return createResponseEntity(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleOthers(Exception ex, WebRequest request) {
        ApiExceptionResponse response = ApiExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        log.error(ex.getMessage(), ex);

        return createResponseEntity(response);
    }

    private ResponseEntity<Object> createResponseEntity(ApiExceptionResponse response) {
        return new ResponseEntity<>(response, response.getStatus());
    }
}