package com.ensim.muremagique;

import com.ensim.muremagique.services.infrastructure.StorageException;
import com.ensim.muremagique.services.infrastructure.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.io.IOException;
import java.nio.file.Files;

@SpringBootApplication
public class MureMagiqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MureMagiqueApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.csrf().disable()
					.authorizeRequests()
					.anyRequest().permitAll();
		}
	}
}
