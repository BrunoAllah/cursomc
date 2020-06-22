package com.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursomc.business.DatabaseBusiness;
import com.cursomc.business.EmailBusiness;
import com.cursomc.business.MockEmailBusiness;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DatabaseBusiness dbBusiness;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		
		if(!strategy.equals("create"))
			return false;
		
		dbBusiness.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailBusiness emailBusiness() {
		return new MockEmailBusiness();
	}

}
