package com.devepsbuddy.backend.service;

import com.devepsbuddy.web.domain.frontend.FeedbackPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.to.address}")
    private String defaultToAddress;
    protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackPojo(FeedbackPojo feedbackPojo){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(defaultToAddress);
        message.setFrom(feedbackPojo.getEmail());
        message.setSubject("[DevopsBuddy] feedback received from"+feedbackPojo.getFirstName()+" "+feedbackPojo.getLastName()+ "!");
        message.setText(feedbackPojo.getFeedback());
        return message;

    }

    @Override
    public void sendFeddbackEmail(FeedbackPojo feedbackPojo) {
        sendGenericEmailMessage(prepareSimpleMailMessageFromFeedbackPojo(feedbackPojo));

    }
}
