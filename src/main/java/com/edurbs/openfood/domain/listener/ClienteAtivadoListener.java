package com.edurbs.openfood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.domain.model.Cliente;
import com.edurbs.openfood.domain.service.notificacao.NivelUrgencia;
import com.edurbs.openfood.domain.service.notificacao.Notificador;
import com.edurbs.openfood.domain.service.notificacao.TipoNotificador;

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
