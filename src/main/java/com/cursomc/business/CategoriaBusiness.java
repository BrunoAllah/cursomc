package com.cursomc.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaDao;


@Service
public class CategoriaBusiness {
	
	@Autowired
	private CategoriaDao categoriaDao; 
	
	public Categoria buscar(Integer id) {
		 Optional<Categoria> obj = categoriaDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}

}
