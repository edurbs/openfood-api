package com.edurbs.openfood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;

import com.edurbs.openfood.core.email.EmailProperties;

public class SandBoxSendEmailService extends SmptSendEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected String[] getRecipients(Message message) {        
        return new String[]{emailProperties.getSandbox().getRecipient()};
    }

 
    
}
