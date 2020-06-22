package com.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursomc.business.DatabaseBusiness;
import com.cursomc.business.EmailBusiness;
import com.cursomc.business.MockEmailBusiness;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DatabaseBusiness dbBusiness;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		dbBusiness.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailBusiness emailBusiness() {
		return new MockEmailBusiness();
	}

}
