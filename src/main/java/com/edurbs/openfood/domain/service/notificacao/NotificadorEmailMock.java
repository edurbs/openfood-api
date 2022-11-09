package com.edurbs.openfood.domain.service.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.domain.model.Cliente;


public class NotificadorEmailMock implements Notificador {

    private String smtp;

    public NotificadorEmailMock(String smtp) {
        this.smtp = smtp;
    }

    @Override
    public void enviar(Cliente cliente, String mensagem) {
        System.out.printf("Seria... notificado cliente %s pelo email %s a mensagem via %s: %s %n",
                cliente.getNome(), cliente.getEmail(), this.getSmtp(), mensagem);
    }

    public String getSmtp() {
        return smtp;
    }

}
