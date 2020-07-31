package com.cursomc.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Cidade;
import com.cursomc.repositories.CidadeDao;


@Service
public class CidadeBusiness {
	
	@Autowired
	private CidadeDao cidadeDao;
	
	public Cidade find(Integer id) {
		 Optional<Cidade> obj = cidadeDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}
	
	public List<Cidade> findAll() {
		return cidadeDao.findAll();
	}
	
	public List<Cidade> findByEstadoId(Integer estadoId){
		return cidadeDao.findCidades(estadoId);
	}

}
