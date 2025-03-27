package com.spring.evalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvaluationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvaluationApiApplication.class, args);
    }

}
