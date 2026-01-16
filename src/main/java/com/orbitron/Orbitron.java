package com.orbitron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Orbitron {
	public static void main(String[] args) {
		SpringApplication.run(Orbitron.class, args);
	}

}
