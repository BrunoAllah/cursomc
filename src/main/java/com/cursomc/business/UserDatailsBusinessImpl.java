package com.cursomc.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.repositories.ClienteDao;
import com.cursomc.security.UserSS;

@Service
public class UserDatailsBusinessImpl implements UserDetailsService {

	@Autowired
	private ClienteDao clienteDao;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = clienteDao.findByEmail(email);
		if (cliente == null)
			throw new UsernameNotFoundException(email);
		
		return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getPassword(), cliente.getPerfis());
	}

}
