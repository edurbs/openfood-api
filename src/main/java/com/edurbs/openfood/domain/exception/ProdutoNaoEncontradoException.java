package com.edurbs.openfood.domain.exception;

public class ProdutoNaoEncontradoException extends EntityNotFoundException {

    public ProdutoNaoEncontradoException(String msg) {
        super(msg);
        
    }

    public ProdutoNaoEncontradoException(Long produtoId) {
        super(String.format("Produto código %d não existe.", produtoId));
        
    }
    
}
