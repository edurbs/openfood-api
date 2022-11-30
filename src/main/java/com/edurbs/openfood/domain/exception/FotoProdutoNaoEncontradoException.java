package com.edurbs.openfood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public FotoProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public FotoProdutoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        super(String.format("Não foi possível encontrar o produto código %d no restaurante código %d.", produtoId,restauranteId));
    }
    
}
