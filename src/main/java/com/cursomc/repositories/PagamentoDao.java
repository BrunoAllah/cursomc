package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Pagamento;

@Repository
public interface PagamentoDao extends JpaRepository<Pagamento, Integer>{

}
