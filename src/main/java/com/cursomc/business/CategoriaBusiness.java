package com.cursomc.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.business.exception.DataIntegrityException;
import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaDao;


@Service
public class CategoriaBusiness {
	
	@Autowired
	private CategoriaDao categoriaDao; 
	
	public Categoria find(Integer id) {
		 Optional<Categoria> obj = categoriaDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaDao.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return categoriaDao.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			categoriaDao.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
		}
	}
	
	public List<Categoria> findAll() {
		return categoriaDao.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaDao.findAll(pageRequest);
	}

}
