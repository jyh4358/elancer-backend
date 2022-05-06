package com.example.elancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan({"com.example.elancer.token.jwt.property", "com.example.elancer.login.auth.property"})
public class ElancerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElancerApplication.class, args);
	}

}
