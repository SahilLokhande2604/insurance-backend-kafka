package com.customersupport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CustomersupportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomersupportServiceApplication.class, args);
	}

}
