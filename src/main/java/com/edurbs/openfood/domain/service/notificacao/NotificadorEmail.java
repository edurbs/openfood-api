package com.edurbs.openfood.domain.service.notificacao;

import com.edurbs.openfood.domain.model.Cliente;

public class NotificadorEmail implements Notificador {

    private String smtp;

    public NotificadorEmail(String smtp) {
        this.smtp = smtp;
    }

    @Override
    public void enviar(Cliente cliente, String mensagem) {
        System.out.printf("Notificando cliente %s pelo email %s a mensagem via %s: %s %n",
                cliente.getNome(), cliente.getEmail(), this.getSmtp(), mensagem);
    }

    public String getSmtp() {
        return smtp;
    }

}
