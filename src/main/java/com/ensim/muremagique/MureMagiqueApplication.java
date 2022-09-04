package com.ensim.muremagique;

import com.ensim.muremagique.services.infrastructure.StorageException;
import com.ensim.muremagique.services.infrastructure.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
public class MureMagiqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MureMagiqueApplication.class, args);
	}

	@Configuration
	public class CorsConfiguration
	{
		@Bean
		public WebMvcConfigurer corsConfigurer()
		{
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
						.allowedMethods("POST", "PUT", "GET", "DELETE", "OPTION").allowedOrigins(
						org.springframework.web.cors.CorsConfiguration.ALL);
				}
			};
		}
	}
}
