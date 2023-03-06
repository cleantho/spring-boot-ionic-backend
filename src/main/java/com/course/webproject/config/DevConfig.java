package com.course.webproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Autowired
	private SeedingTestDb seeding;
	
	@Bean
	public boolean instantiateDatabase() {
		if(!strategy.equals("create")) {
			return false;
		}
		seeding.instantiateTestDataBase();
		return true;
	}
}
