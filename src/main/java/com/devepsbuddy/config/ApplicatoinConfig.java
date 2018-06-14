package com.devepsbuddy.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.devepsbuddy.backend.persistence.repositories")
@EntityScan(basePackages = "com.devepsbuddy.backend.persistence.domain.backend")
@EnableTransactionManagement
@PropertySource("file:///${user.home}/.devopsbuddy/application-common.properties")
public class ApplicatoinConfig {


}
