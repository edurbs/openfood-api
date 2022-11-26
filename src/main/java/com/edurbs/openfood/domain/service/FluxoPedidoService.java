package com.edurbs.openfood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = cadastroPedidoService.buscar(codigoPedido);

        pedido.confirmar();

    }

    @Transactional
    public void entregar(String codigoPedido) {
        var pedido = cadastroPedidoService.buscar(codigoPedido);

        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        var pedido = cadastroPedidoService.buscar(codigoPedido);

        pedido.cancelar();

    }

}
