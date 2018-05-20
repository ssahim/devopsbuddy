package com.devepsbuddy.config;

import com.devepsbuddy.backend.service.EmailService;
import com.devepsbuddy.backend.service.MocEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource("file:///${user.home}/.devopsbuddy/application-dev.properties")
public class DevelopmentConfig {


    @Bean
    public EmailService emailService(){
        return new MocEmailService();
    }
}
