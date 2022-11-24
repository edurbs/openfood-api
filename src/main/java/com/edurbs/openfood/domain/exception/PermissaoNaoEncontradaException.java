package com.edurbs.openfood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeEmUsoException{

    public PermissaoNaoEncontradaException(String message) {
        super(message);        
    }

    public PermissaoNaoEncontradaException(Long id) {
        super(String.format("Permissão código %d não existe.", id));        
    }
    
}
