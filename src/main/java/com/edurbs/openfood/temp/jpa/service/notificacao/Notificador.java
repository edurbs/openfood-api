package com.edurbs.openfood.temp.jpa.service.notificacao;

import org.springframework.stereotype.Component;

import com.edurbs.openfood.temp.jpa.Cliente;

@Component
public interface Notificador {
    public void enviar (Cliente cliente, String mensagem);
}
