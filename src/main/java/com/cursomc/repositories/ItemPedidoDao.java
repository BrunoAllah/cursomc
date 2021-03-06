package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.ItemPedido;

@Repository
public interface ItemPedidoDao extends JpaRepository<ItemPedido, Integer>{

}
