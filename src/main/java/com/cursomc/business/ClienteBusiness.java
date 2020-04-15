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
import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.repositories.ClienteDao;


@Service
public class ClienteBusiness {
	
	@Autowired
	private ClienteDao clienteDao; 
	
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = clienteDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}
	
	public Cliente update(Cliente obj) {
		Cliente cliente = find(obj.getId());
		updateData(cliente, obj);
		return clienteDao.save(cliente);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			clienteDao.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll() {
		return clienteDao.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteDao.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}


}
