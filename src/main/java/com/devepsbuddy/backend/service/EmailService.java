package com.devepsbuddy.backend.service;

import com.devepsbuddy.web.domain.frontend.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public void sendFeddbackEmail(FeedbackPojo feedbackPojo);

    public  void sendGenericEmailMessage(SimpleMailMessage simpleMailMessage);
}
