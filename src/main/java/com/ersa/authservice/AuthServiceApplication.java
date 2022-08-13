package com.ersa.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

    String token = "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";

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
