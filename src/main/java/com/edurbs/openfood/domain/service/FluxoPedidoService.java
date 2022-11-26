package com.edurbs.openfood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Transactional
    public void confirmar(Long pedidoId) {
        var pedido = cadastroPedidoService.buscar(pedidoId);

        pedido.confirmar();

    }

    @Transactional
    public void entregar(Long pedidoId) {
        var pedido = cadastroPedidoService.buscar(pedidoId);

        pedido.entregar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        var pedido = cadastroPedidoService.buscar(pedidoId);

        pedido.cancelar();

    }

}
