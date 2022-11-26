package com.edurbs.openfood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {


    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format("Pedido código %s não existe.", codigoPedido));
        
    }


    
}
