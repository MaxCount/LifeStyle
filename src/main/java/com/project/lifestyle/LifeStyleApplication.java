package com.project.lifestyle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com")
@EnableAsync
public class LifeStyleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeStyleApplication.class, args);
    }

}
