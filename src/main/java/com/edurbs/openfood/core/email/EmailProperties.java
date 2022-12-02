package com.edurbs.openfood.core.email;

import jakarta.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("openfood.email")
public class EmailProperties {

    @NotNull
    private String sender;

    private MailSenderType impl;

    private Sandbox sandbox = new Sandbox();

    @Getter
    @Setter
    public class Sandbox {
        private String recipient;
    }

    public enum MailSenderType{
        SMTP, FAKE, SANDBOX
    }
}
