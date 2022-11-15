package com.edurbs.openfood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(String msg) {
        super(msg);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(String.format("Restaurante código %d não encontrado", id));
    }
    
}
