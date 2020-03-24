package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Endereco;

@Repository
public interface EnderecoDao extends JpaRepository<Endereco, Integer>{

}
