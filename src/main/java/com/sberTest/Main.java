package com.sberTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EntityScan(basePackageClasses = Main.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(Main.class).build();
        application.run(args);
    }
}