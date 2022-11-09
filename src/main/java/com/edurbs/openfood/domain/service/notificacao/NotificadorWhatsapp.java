package com.edurbs.openfood.domain.service.notificacao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.edurbs.openfood.domain.model.Cliente;

@Component
@TipoNotificador(NivelUrgencia.NENHUMA)
public class NotificadorWhatsapp implements Notificador {

    @Override
    public String enviar(Cliente cliente, String mensagem) {
        
        return String.format("Enviando mensagem Whatsapp %s para o cliente %s: %s %n", cliente.getTelefone(), cliente.getNome(), mensagem);
    }
    
}
