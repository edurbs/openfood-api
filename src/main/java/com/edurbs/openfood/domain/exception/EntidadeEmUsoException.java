package com.edurbs.openfood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntidadeEmUsoException extends ResponseStatusException {

    public EntidadeEmUsoException(HttpStatus status, String message) {
        super(status, message);       
    }

    public EntidadeEmUsoException(String message) {
        this(HttpStatus.CONFLICT, message);       
    }
    
}
