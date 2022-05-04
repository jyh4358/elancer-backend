package com.example.elancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.elancer.jwt.property")
public class ElancerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElancerApplication.class, args);
	}

}
