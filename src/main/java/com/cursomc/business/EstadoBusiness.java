package com.cursomc.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Estado;
import com.cursomc.repositories.EstadoDao;


@Service
public class EstadoBusiness {
	
	@Autowired
	private EstadoDao estadoDao;
	
	public Estado find(Integer id) {
		 Optional<Estado> obj = estadoDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}
	
	public List<Estado> findAll() {
		return estadoDao.findAllByOrderByNome();
	}

}
