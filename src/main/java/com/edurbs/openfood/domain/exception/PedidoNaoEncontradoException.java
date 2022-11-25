package com.edurbs.openfood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String msg) {
        super(msg);
        
    }

    public PedidoNaoEncontradoException(Long id) {
        this(String.format("Pedido código %d não existe.", id));
        
    }


    
}
