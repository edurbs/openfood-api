package com.edurbs.openfood.domain.service.notificacao;

import org.springframework.stereotype.Component;

import com.edurbs.openfood.domain.model.Cliente;

@Component
public interface Notificador {
    public String enviar (Cliente cliente, String mensagem);
}
