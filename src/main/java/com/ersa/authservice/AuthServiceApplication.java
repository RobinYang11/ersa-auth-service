package com.ersa.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	String name ="robin";
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
