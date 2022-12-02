package com.edurbs.openfood.core.email;

import javax.validation.constraints.NotNull;

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

    public enum MailSenderType{
        SMTP, FAKE
    }
}
