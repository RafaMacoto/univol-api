package com.univol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UnivolApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnivolApplication.class, args);
	}

}
