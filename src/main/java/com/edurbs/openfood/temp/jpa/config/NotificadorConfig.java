package com.edurbs.openfood.temp.jpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.edurbs.openfood.temp.jpa.service.notificacao.NivelUrgencia;
import com.edurbs.openfood.temp.jpa.service.notificacao.NotificadorEmail;
import com.edurbs.openfood.temp.jpa.service.notificacao.NotificadorEmailMock;
import com.edurbs.openfood.temp.jpa.service.notificacao.NotificadorSMS;
import com.edurbs.openfood.temp.jpa.service.notificacao.TipoNotificador;

@Configuration
public class NotificadorConfig {

    /*@Value("${notificador.email.host}")
    private String stmp;

    public String getStmp() {
        return stmp;
    }*/

    @Autowired
    private NotificadorProperties notificadorProperties;

    @Bean
    @TipoNotificador(NivelUrgencia.URGENTE)
    public NotificadorSMS notificadorSMS (){
        NotificadorSMS notificadorSMS = new NotificadorSMS("tim.com.br");
        notificadorSMS.setCaixaAlta(false);
        return notificadorSMS;
    }

    @Bean
    @TipoNotificador(NivelUrgencia.NORMAL)
    @Profile("prod")
    // @Qualifier("email") 
    public NotificadorEmail notificadorEmail (){
        //return new NotificadorEmail(getStmp());
        return new NotificadorEmail(notificadorProperties.getHostServidor());
        
    }

    @Bean
    @TipoNotificador(NivelUrgencia.NORMAL)
    @Profile("dev")
    public NotificadorEmailMock notificadorEmailMock(){
        
        return new NotificadorEmailMock("Mock "+notificadorProperties.getHostServidor());
    }
}
