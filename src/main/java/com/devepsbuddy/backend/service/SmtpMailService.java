package com.devepsbuddy.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpMailService extends AbstractEmailService {
    private static final Logger LOG=LoggerFactory.getLogger(SmtpMailService.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage simpleMailMessage) {

        LOG.debug("Sending Email...");
        mailSender.send(simpleMailMessage);

        LOG.info("Email sent");
    }
}
