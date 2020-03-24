package com.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.Estado;
import com.cursomc.domain.Produto;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.repositories.CategoriaDao;
import com.cursomc.repositories.CidadeDao;
import com.cursomc.repositories.ClienteDao;
import com.cursomc.repositories.EnderecoDao;
import com.cursomc.repositories.EstadoDao;
import com.cursomc.repositories.ProdutoDao;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaDao categoriaDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Autowired
	private EstadoDao estadoDao;
	
	@Autowired
	private CidadeDao cidadeDao;
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private EnderecoDao enderecoDao;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p2.getCategorias().add(cat1);
		
		categoriaDao.saveAll(Arrays.asList(cat1, cat2));
		produtoDao.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoDao.saveAll(Arrays.asList(est1, est2));
		cidadeDao.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria", "maria@gmail.com", "00000000", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("1100000000", "1111111111"));
		
		Endereco e1 = new Endereco(null, "Av. Paulista", "1500", "Apto 23", "Jardins", "00000-000", cli1, c1);
		Endereco e2 = new Endereco(null, "Av. Paulista", "3425", "Apto 76", "Consolação", "00000-000", cli1, c2);		
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));		
		
		clienteDao.save(cli1);
		enderecoDao.saveAll(Arrays.asList(e1, e2));
	}

}
