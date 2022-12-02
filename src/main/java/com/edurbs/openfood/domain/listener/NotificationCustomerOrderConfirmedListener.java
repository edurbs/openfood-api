package com.edurbs.openfood.domain.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.edurbs.openfood.domain.event.OrderConfirmedEvent;
import com.edurbs.openfood.domain.model.Pedido;
import com.edurbs.openfood.domain.service.SendEmailService;

@Component
public class NotificationCustomerOrderConfirmedListener {
    
    @Autowired
    private SendEmailService sendEmailService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void whenConfirmOrder (OrderConfirmedEvent event){
        Pedido pedido = event.getPedido();
        
        var message = SendEmailService.Message.builder()
                .subject(pedido.getRestaurante().getNome()+" - Pedido confirmado")
                .body("order-confirmed.html")
                .variable("pedido", pedido)
                .recipient(pedido.getCliente().getEmail())
                .build();

        sendEmailService.send(message);
    }
}
