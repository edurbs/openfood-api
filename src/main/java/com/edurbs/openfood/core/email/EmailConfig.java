package com.edurbs.openfood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.edurbs.openfood.core.email.EmailProperties.MailSenderType;
import com.edurbs.openfood.domain.service.SendEmailService;
import com.edurbs.openfood.infrastructure.service.email.FakeSendEmailService;
import com.edurbs.openfood.infrastructure.service.email.SmptSendEmailService;

@Configuration
public class EmailConfig {
 
    @Autowired
    private EmailProperties emailProperties;
    @Bean
    public SendEmailService sendEmailService(){

        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeSendEmailService();
            case SMTP:
                return new SmptSendEmailService();
            default:
                return null;
        }
        
    }

    
}
