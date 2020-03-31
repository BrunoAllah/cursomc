package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Cidade;

@Repository
public interface CidadeDao extends JpaRepository<Cidade, Integer>{

}