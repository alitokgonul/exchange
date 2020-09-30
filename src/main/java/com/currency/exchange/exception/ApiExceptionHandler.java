package com.currency.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>(e.getBindingResult().getFieldErrors().size());

        e.getBindingResult().getFieldErrors().forEach(ex -> {
            errors.add(ex.getField() + " : " + ex.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FixerClientException.class)
    public ResponseEntity<Object> handleFixerClientException(FixerClientException e) {

        return new ResponseEntity<>(createApiException(e, HttpStatus.FAILED_DEPENDENCY), HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {

        return new ResponseEntity<>(createApiException(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    private ApiException createApiException(Exception exception, HttpStatus status) {
        return ApiException.builder()
                .message(exception.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now(ZoneId.of("GMT+3")))
                .build();
    }

}
