package com.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursomc.business.EmailBusiness;
import com.cursomc.business.MockEmailBusiness;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Bean
	public EmailBusiness emailBusiness() {
		return new MockEmailBusiness();
	}

}
