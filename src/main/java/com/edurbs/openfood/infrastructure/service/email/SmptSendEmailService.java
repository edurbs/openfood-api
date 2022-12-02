package com.edurbs.openfood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.edurbs.openfood.core.email.EmailProperties;
import com.edurbs.openfood.domain.service.SendEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;

public class SmptSendEmailService implements SendEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freeMarkerConfig;

    @Override
    public void send(Message message) {
        try {

            String body = processTemplate(message);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            
            helper.setFrom(emailProperties.getSender());
            helper.setTo(getRecipients(message));
            helper.setSubject(message.getSubject());
            helper.setText(body, true);


            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar o email", e);
        }
    }

    protected String[] getRecipients(Message message){
        return message.getRecipients().toArray(new String[0]);
    }

    protected String processTemplate(Message message){

        try {
            Template template = freeMarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
            
        } catch (Exception e) {
            throw new EmailException("Não foi possívle montar o template do email.", e);
        }

    }
    
}
