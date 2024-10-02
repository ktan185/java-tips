package com.javatips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import jakarta.annotation.PostConstruct;
import me.paulschwarz.springdotenv.DotenvPropertySource;

@Configuration
public class DotEnvConfig {

    @Autowired
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void init() {
        DotenvPropertySource.addToEnvironment(environment);
    }
}
