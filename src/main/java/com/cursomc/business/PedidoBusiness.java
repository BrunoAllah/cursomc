package com.cursomc.business;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.repositories.ItemPedidoDao;
import com.cursomc.repositories.PagamentoDao;
import com.cursomc.repositories.PedidoDao;


@Service
public class PedidoBusiness {
	
	@Autowired
	private PedidoDao pedidoDao; 
	
	@Autowired
	private BoletoBusiness boletoBusiness;
	
	@Autowired
	private PagamentoDao pagamentoDao;
	
	@Autowired
	private ProdutoBusiness produtoBusiness;
	
	@Autowired
	private ItemPedidoDao itemPedidoDao;
	
	public Pedido find(Integer id) {
		 Optional<Pedido> obj = pedidoDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoBusiness.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		pedidoDao.save(obj);
		pagamentoDao.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoBusiness.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoDao.saveAll(obj.getItens());
		return obj;
	}
}
