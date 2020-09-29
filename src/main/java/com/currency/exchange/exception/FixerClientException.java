package com.currency.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class FixerClientException  extends RuntimeException{

    public FixerClientException(String message) {
        super(message);
    }
}
