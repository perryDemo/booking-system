package com.agoda.clone.agoda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AgodaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgodaApplication.class, args);
	}

}
