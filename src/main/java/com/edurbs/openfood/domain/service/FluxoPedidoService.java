package com.edurbs.openfood.domain.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.domain.repository.PedidoRepository;

@Component
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;


    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = cadastroPedidoService.buscar(codigoPedido);

        pedido.confirmar();

        pedidoRepository.save(pedido); // to send the event
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
