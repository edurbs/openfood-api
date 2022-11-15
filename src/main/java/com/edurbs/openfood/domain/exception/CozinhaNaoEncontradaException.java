package com.edurbs.openfood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

    public CozinhaNaoEncontradaException(String msg) {
        super(msg);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this(String.format("Cozinha código %d não encontrada", id));
    }
    
}
