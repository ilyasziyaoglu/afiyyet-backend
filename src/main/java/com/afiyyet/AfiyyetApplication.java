package com.afiyyet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties
@OpenAPIDefinition(info = @Info(title = "My API", version = "1.0.0"))
public class AfiyyetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfiyyetApplication.class, args);
	}

}
