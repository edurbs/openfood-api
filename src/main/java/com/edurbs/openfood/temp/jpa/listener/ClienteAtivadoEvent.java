package com.edurbs.openfood.temp.jpa.listener;

import org.springframework.stereotype.Component;

import com.edurbs.openfood.temp.jpa.Cliente;


public class ClienteAtivadoEvent {

    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public ClienteAtivadoEvent(Cliente cliente) {
        this.cliente = cliente;
    }

    
    
}
