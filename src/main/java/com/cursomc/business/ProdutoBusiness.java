package com.cursomc.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
import com.cursomc.repositories.CategoriaDao;
import com.cursomc.repositories.ProdutoDao;


@Service
public class ProdutoBusiness {
	
	@Autowired
	private ProdutoDao produtoDao; 
	
	@Autowired
	private CategoriaDao categoriaDao;
	
	public Produto find(Integer id) {
		 Optional<Produto> obj = produtoDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaDao.findAllById(ids);
		return produtoDao.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
