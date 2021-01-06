package com.valdir.jwt.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.jwt.domain.Categoria;
import com.valdir.jwt.domain.Produto;
import com.valdir.jwt.dtos.ProdutoDTO;
import com.valdir.jwt.repositories.CategoriaRepository;
import com.valdir.jwt.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto findById(Integer id) {
		Optional<Produto> obj = repository.findById(id);
		return obj.orElse(null);
	}
	
	public List<Produto> findAll() {
		return repository.findAll();
	}
	
	public Produto create(Integer id_cli, ProdutoDTO objDTO) {
		Categoria cat = categoriaService.findById(id_cli);
		Produto newObj = new Produto(id_cli, objDTO.getNome(), objDTO.getPreco());
		newObj.getCategorias().add(cat);
		cat.getProdutos().add(newObj);
		categoriaRepository.save(cat);
		return repository.save(newObj);
	}

	public Produto update(Integer id, @Valid ProdutoDTO objDTO) {
		Produto obj = this.findById(id);
		this.fromDTO(obj, objDTO);
		return repository.save(obj);
	}

	private void fromDTO(Produto obj, @Valid ProdutoDTO objDTO) {
		obj.setNome(objDTO.getNome());
	}

	public void delete(Integer id) {
		this.findById(id);
		repository.deleteById(id);
	}
	

}
