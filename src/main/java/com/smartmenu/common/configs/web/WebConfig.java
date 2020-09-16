package com.smartmenu.common.configs.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-26
 */

@Configuration
public class WebConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer2() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*")
						.allowedOrigins("*");
			}
		};
	}
}
