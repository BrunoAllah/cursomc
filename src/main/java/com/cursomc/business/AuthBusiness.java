package com.cursomc.business;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Cliente;
import com.cursomc.repositories.ClienteDao;

@Service
public class AuthBusiness {
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailBusiness emailBusiness;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteDao.findByEmail(email);
		if (cliente == null)
			throw new ObjectNotFoundException("Email não encontrado");
		
		String newPass = newPassword();
		cliente.setPassword(pe.encode(newPass));
		
		clienteDao.save(cliente);
		emailBusiness.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		switch (opt) {
		case 0: // retorna um digito
			return (char) (random.nextInt(10) + 48);
		case 1: // retorna letra maiuscula
			return (char) (random.nextInt(26) + 65);
		default: // retorna letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}

}
