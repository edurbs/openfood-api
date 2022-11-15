package com.edurbs.openfood.domain.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
        
    }

    public NegocioException(String mensagem) {
        super(mensagem);
        
    }
    
}
