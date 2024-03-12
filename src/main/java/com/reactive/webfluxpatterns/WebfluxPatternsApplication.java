package com.reactive.webfluxpatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.reactive.webfluxpatterns.sec03")
public class WebfluxPatternsApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebfluxPatternsApplication.class, args);
	}
}
