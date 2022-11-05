package com.sberTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EntityScan(basePackageClasses = Application.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(Application.class).build();
        application.run(args);
    }
}