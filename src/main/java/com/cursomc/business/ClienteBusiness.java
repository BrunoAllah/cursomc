package com.cursomc.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Cliente;
import com.cursomc.repositories.ClienteDao;


@Service
public class ClienteBusiness {
	
	@Autowired
	private ClienteDao clienteDao; 
	
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = clienteDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}

}
