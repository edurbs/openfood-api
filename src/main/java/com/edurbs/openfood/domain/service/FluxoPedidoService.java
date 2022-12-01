package com.edurbs.openfood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.domain.service.SendEmailService.Message;

@Component
public class FluxoPedidoService {

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Autowired
    private SendEmailService sendEmailService;

    @Transactional
    public void confirmar(String codigoPedido) {
        var pedido = cadastroPedidoService.buscar(codigoPedido);

        pedido.confirmar();

        var message = Message.builder()
                .subject(pedido.getRestaurante().getNome()+" - Pedido confirmado")
                .body("order-confirmed.html")
                .variable("pedido", pedido)
                .recipient(pedido.getCliente().getEmail())
                .build();

        sendEmailService.send(message);

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
