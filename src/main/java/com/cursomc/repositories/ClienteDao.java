package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Cliente;

@Repository
public interface ClienteDao extends JpaRepository<Cliente, Integer>{

	@Transactional
	Cliente findByEmail(String email);
}
