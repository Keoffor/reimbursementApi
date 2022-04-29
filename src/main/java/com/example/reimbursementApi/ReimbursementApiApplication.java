package com.example.reimbursementApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ReimbursementApiApplication {

	private static final Logger logger = LoggerFactory.getLogger(ReimbursementApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReimbursementApiApplication.class, args);
	}


	@Bean
	public CommandLineRunner runner() {
		return args -> {
			MDC.put("events", "Application Startup");
			logger.info("Starting...");
			logger.info("Complete");
			MDC.clear();
		};
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
