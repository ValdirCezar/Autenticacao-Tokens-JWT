package com.valdir.jwt.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.jwt.domain.Cliente;
import com.valdir.jwt.dtos.ClienteDTO;
import com.valdir.jwt.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElse(null);
	}
	
	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Cliente create(ClienteDTO objDTO) {
		Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getSenha());
		return repository.save(newObj);
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente obj = this.findById(id);
		this.fromDTO(obj, objDTO);
		return repository.save(obj);
	}

	private void fromDTO(Cliente obj, @Valid ClienteDTO objDTO) {
		obj.setNome(objDTO.getNome());
		obj.setEmail(objDTO.getEmail());
		obj.setSenha(objDTO.getSenha());
	}

	public void delete(Integer id) {
		this.findById(id);
		repository.deleteById(id);
	}
	

}
