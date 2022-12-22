package com.edurbs.openfood.domain.exception;

public class EntidadeEmUsoException extends BusinessException {

    public EntidadeEmUsoException(String message) {
        super( message);       
    }

    public EntidadeEmUsoException(String message, Throwable causa) {
        super(message, causa);       
    }
    
}
