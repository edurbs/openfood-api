package com.edurbs.openfood.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.edurbs.openfood.domain.service.notificacao.NivelUrgencia;
import com.edurbs.openfood.domain.service.notificacao.NotificadorEmail;
import com.edurbs.openfood.domain.service.notificacao.NotificadorEmailMock;
import com.edurbs.openfood.domain.service.notificacao.NotificadorSMS;
import com.edurbs.openfood.domain.service.notificacao.TipoNotificador;

@Configuration
public class NotificadorSMSConfig {

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
        return new NotificadorEmail("smtp.gmail.com.br");
        
    }

    @Bean
    @TipoNotificador(NivelUrgencia.NORMAL)
    @Profile("dev")
    public NotificadorEmailMock notificadorEmailMock(){
        return new NotificadorEmailMock("smtp.falso.com.br");
    }
}
