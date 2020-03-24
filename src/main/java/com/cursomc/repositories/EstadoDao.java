package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Estado;

@Repository
public interface EstadoDao extends JpaRepository<Estado, Integer>{

}
