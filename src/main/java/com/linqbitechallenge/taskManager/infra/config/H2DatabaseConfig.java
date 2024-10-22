package com.linqbitechallenge.taskManager.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.linqbitechallenge.taskManager.infra.persistence")
public class H2DatabaseConfig {

}
