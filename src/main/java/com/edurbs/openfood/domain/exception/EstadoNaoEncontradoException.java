package com.edurbs.openfood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public EstadoNaoEncontradoException(String msg) {
        super(msg);       
    }

    public EstadoNaoEncontradoException(Long estadoId) {
        this(String.format("Estado código %d não existe.", estadoId));       
    }
    
}
