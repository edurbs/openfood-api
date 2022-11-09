package com.edurbs.openfood.domain.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EnvioNFAtivacaoListener {
    
    @EventListener
    public void enviaNFClienteAtivado(ClienteAtivadoEvent event){
        System.out.printf("Envia NF de ativação para o cliente %s.", event.getCliente().getNome());
    }

}
