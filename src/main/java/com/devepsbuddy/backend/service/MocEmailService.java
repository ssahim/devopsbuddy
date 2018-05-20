package com.devepsbuddy.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import sun.rmi.runtime.Log;

public class MocEmailService extends AbstractEmailService {

    private static final Logger LOG=LoggerFactory.getLogger(MocEmailService.class);
    @Override
    public void sendGenericEmailMessage(SimpleMailMessage simpleMailMessage) {
        LOG.debug("Simulating an email service....");
        LOG.info(simpleMailMessage.toString());
        LOG.debug("Email Sent");
    }
}
