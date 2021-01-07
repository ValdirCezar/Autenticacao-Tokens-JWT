package com.valdir.jwt.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.valdir.jwt.domain.Categoria;
import com.valdir.jwt.domain.Cliente;
import com.valdir.jwt.domain.Produto;
import com.valdir.jwt.enums.Perfil;
import com.valdir.jwt.repositories.CategoriaRepository;
import com.valdir.jwt.repositories.ClienteRepository;
import com.valdir.jwt.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	
	public void instantiateDatabase() {
		
		// -------- Cliente 1 ---------
		
		Cliente cli1 = new Cliente(null, "Valdir Cezar", "valdir@email.com", pe.encode("123"));
		cli1.addPerfis(Perfil.ADMIN);
		
		Categoria cat1 = new Categoria(null, "Informática", cli1);
		Categoria cat2 = new Categoria(null, "Escritório", cli1);
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de Escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true Color", 1200.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3, p4));
		cat2.getProdutos().addAll(Arrays.asList(p5, p6, p7));
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().add(cat1);
		p3.getCategorias().add(cat1);
		p4.getCategorias().add(cat1);
		p5.getCategorias().add(cat2);
		p6.getCategorias().add(cat2);
		p7.getCategorias().add(cat2);
		
		// -------- Cliente 2 ---------
		
		Cliente cli2 = new Cliente(null, "Albert Einstein", "einstein@email.com", pe.encode("123"));

		Categoria cat3 = new Categoria(null, "Cama mesa e banho", cli2);
		
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		
		cat3.getProdutos().addAll(Arrays.asList(p8, p9, p10));
		
		p8.getCategorias().add(cat3);
		p9.getCategorias().add(cat3);
		p10.getCategorias().add(cat3);
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));

	}

}
