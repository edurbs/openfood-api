package com.edurbs.openfood.domain.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String mensagem, Throwable causa) {
        super(mensagem, causa);
        
    }

    public BusinessException(String mensagem) {
        super(mensagem);
        
    }
    
}
