package com.devepsbuddy.config;

import com.devepsbuddy.backend.service.EmailService;
import com.devepsbuddy.backend.service.SmtpMailService;
import com.devepsbuddy.web.domain.frontend.FeedbackPojo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;

@Configuration
@Profile("prod")
@PropertySource("file:///${user.home}/.devopsbuddy/application-prod.properties")
public class ProductionConfig {

    @Bean
    public EmailService emailService(){
        return new SmtpMailService();
    }
}
