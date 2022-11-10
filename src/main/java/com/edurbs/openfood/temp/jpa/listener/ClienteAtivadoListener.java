package com.edurbs.openfood.temp.jpa.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.temp.jpa.Cliente;
import com.edurbs.openfood.temp.jpa.service.notificacao.NivelUrgencia;
import com.edurbs.openfood.temp.jpa.service.notificacao.Notificador;
import com.edurbs.openfood.temp.jpa.service.notificacao.TipoNotificador;

@Component
public class ClienteAtivadoListener {

    @TipoNotificador(NivelUrgencia.NORMAL)
    @Autowired
    private Notificador notificador;

    @EventListener
    public void notificaClienteAtivado(ClienteAtivadoEvent event){
        notificador.enviar(event.getCliente(), "Cadastro no sistema openFood foi ativado");
    }
}
