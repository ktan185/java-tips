package com.javatips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.javatips.service.OpenAIService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // This is for testing, we can refactor later.
        ApplicationContext context = SpringApplication.run(Application.class, args);
        OpenAIService service = context.getBean(OpenAIService.class);
        service.requestForJavaTip();
    }
}
