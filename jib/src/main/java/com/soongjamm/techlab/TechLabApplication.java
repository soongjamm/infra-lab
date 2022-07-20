package com.soongjamm.techlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TechLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechLabApplication.class, args);
	}

}
