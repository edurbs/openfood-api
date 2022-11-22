package com.edurbs.openfood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    
    public GrupoNaoEncontradoException(String msg){
        super(msg);
    }
    
    public GrupoNaoEncontradoException(Long id) {
        this(String.format("Cidade código %d não encontrada", id));
    }
    
}
