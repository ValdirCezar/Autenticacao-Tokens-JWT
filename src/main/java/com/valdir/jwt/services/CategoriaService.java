package com.valdir.jwt.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.jwt.domain.Categoria;
import com.valdir.jwt.domain.Cliente;
import com.valdir.jwt.dtos.CategoriaDTO;
import com.valdir.jwt.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	@Autowired
	private ClienteService clienteService;
	
	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElse(null);
	}
	
	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Categoria create(Integer id_cli, CategoriaDTO objDTO) {
		Cliente cli = clienteService.findById(id_cli);
		Categoria newObj = new Categoria(null, objDTO.getNome(), cli);
		return repository.save(newObj);
	}

	public Categoria update(Integer id, @Valid CategoriaDTO objDTO) {
		Categoria obj = this.findById(id);
		this.fromDTO(obj, objDTO);
		return repository.save(obj);
	}

	private void fromDTO(Categoria obj, @Valid CategoriaDTO objDTO) {
		obj.setNome(objDTO.getNome());
	}

	public void delete(Integer id) {
		this.findById(id);
		repository.deleteById(id);
	}
	

}
