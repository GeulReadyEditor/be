package com.encore.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location=" + "classpath:application.yml,"
			+ "classpath:application-credentials.yml," + "classpath:application-aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(BackendApplication.class).properties(APPLICATION_LOCATIONS).run(args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
