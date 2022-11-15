package com.edurbs.openfood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

    protected EntidadeNaoEncontradaException(String msg) {
        super( msg);
    }

    protected EntidadeNaoEncontradaException(String msg, Throwable causa) {
        super(msg, causa);
    }

}
