package com.cursomc.business;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.business.exception.AuthorizationException;
import com.cursomc.business.exception.ObjectNotFoundException;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.repositories.ItemPedidoDao;
import com.cursomc.repositories.PagamentoDao;
import com.cursomc.repositories.PedidoDao;
import com.cursomc.security.UserSS;


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
	
	@Autowired
	private ClienteBusiness clienteBusiness;
	
	@Autowired
	private EmailBusiness emailBusiness;
	
	public Pedido find(Integer id) {
		 Optional<Pedido> obj = pedidoDao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado id: " + id));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteBusiness.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoBusiness.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = pedidoDao.save(obj);
		pagamentoDao.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoBusiness.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoDao.saveAll(obj.getItens());
		emailBusiness.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserBusiness.authenticated();
		if (user == null)
			throw new AuthorizationException("Acesso negado");
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteBusiness.find(user.getId());
		return pedidoDao.findByCliente(cliente, pageRequest);
	}
}
