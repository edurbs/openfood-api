package com.edurbs.openfood.domain.exception;

public class GrupoNaoEncontradoException extends EntityNotFoundException {
    
    public GrupoNaoEncontradoException(String msg){
        super(msg);
    }
    
    public GrupoNaoEncontradoException(Long id) {
        this(String.format("Grupo código %d não encontrado.", id));
    }
    
}
