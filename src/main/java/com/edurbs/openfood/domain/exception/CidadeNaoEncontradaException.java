package com.edurbs.openfood.domain.exception;

public class CidadeNaoEncontradaException extends EntityNotFoundException{

    public CidadeNaoEncontradaException(String msg) {
        super(msg);
    }
    
    public CidadeNaoEncontradaException(Long id) {
        this(String.format("Cidade código %d não encontrada", id));
    }
}
