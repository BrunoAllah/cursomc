package com.cursomc.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Pedido;
import com.cursomc.repositories.PedidoDao;


@Service
public class PedidoBusiness {
	
	@Autowired
	private PedidoDao pedidoDao; 
	
	public Pedido find(Integer id) {
		 Optional<Pedido> obj = pedidoDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}

}
