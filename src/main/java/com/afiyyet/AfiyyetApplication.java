package com.afiyyet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties
public class AfiyyetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfiyyetApplication.class, args);
	}

}
