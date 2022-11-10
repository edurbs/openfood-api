package com.edurbs.openfood.temp.jpa.service.notificacao;


import org.springframework.context.annotation.Bean;

import com.edurbs.openfood.temp.jpa.Cliente;


public class NotificadorSMS implements Notificador {

    private boolean caixaAlta;
    private String smsGateway;
    
    
    public NotificadorSMS(String smsGateway) {
        
        this.smsGateway = smsGateway;
    }    

    @Override
    public void enviar(Cliente cliente, String mensagem){
        String sms = String.format("Notificando cliente %s pelo telefone %s a mensagem pelo gateway %s: %s %n",
                cliente.getNome(), cliente.getTelefone(), this.smsGateway, mensagem);
        
        if(this.caixaAlta)
            sms = sms.toUpperCase();

        System.out.println(sms);
        
    }

    public void setCaixaAlta(boolean caixaAlta) {
        this.caixaAlta = caixaAlta;
    }
    
}
