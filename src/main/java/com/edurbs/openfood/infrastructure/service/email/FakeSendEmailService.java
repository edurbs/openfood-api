package com.edurbs.openfood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;

import com.edurbs.openfood.core.email.EmailProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeSendEmailService extends SmptSendEmailService{
    
    @Autowired
    private EmailProperties emailProperties;

    @Override    
    public void send(Message message) {
        
        log.info("From " + emailProperties.getSender());

        log.info("To: ");
        message.getRecipients().stream().forEach(log::info);

        log.info("Subject: " + message.getSubject());
        
        String body = processTemplate(message);

        log.info(body);
    }
    
}
