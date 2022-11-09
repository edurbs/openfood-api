package com.edurbs.openfood.domain.listener;

import org.springframework.stereotype.Component;

import com.edurbs.openfood.domain.model.Cliente;


public class ClienteAtivadoEvent {

    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public ClienteAtivadoEvent(Cliente cliente) {
        this.cliente = cliente;
    }

    
    
}
