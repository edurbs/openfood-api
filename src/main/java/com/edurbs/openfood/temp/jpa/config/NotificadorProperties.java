package com.edurbs.openfood.temp.jpa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificador.email")
public class NotificadorProperties {
    
    /**
     * Nome do servidor de email de notificação
     */
    private String hostServidor;

    /**
     * Porta do servidor de email de notificação
     */
    private int portaServidor=25;

    public String getHostServidor() {
        return hostServidor;
    }
    public int getPortaServidor() {
        return portaServidor;
    }
    public void setHostServidor(String hostServidor) {
        this.hostServidor = hostServidor;
    }
    public void setPortaServidor(int portaServidor) {
        this.portaServidor = portaServidor;
    }

    
    
}
