package com.currency.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>(e.getBindingResult().getFieldErrors().size());

        e.getBindingResult().getFieldErrors().forEach(ex -> {
            errors.add(ex.getField() + " : " + ex.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = FixerClientException.class)
    public ResponseEntity<Object> handleFixerClientException(FixerClientException e) {
        HttpStatus status = HttpStatus.FAILED_DEPENDENCY;

        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now(ZoneId.of("GMT+3")))
                .build();

        return new ResponseEntity<>(apiException, status);
    }

}
