package com.course.webproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private SeedingTestDb seeding;

	@Bean
	public boolean instantiateDatabase() {
		seeding.instantiateTestDataBase();
		return true;
	}
}
