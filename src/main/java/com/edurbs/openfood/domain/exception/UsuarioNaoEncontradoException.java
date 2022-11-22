package com.edurbs.openfood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(String msg) {
        super(msg);        
    }

    public UsuarioNaoEncontradoException(Long id) {
        super(String.format("Usuário código %d não encontrado", id));
    }
    
}
