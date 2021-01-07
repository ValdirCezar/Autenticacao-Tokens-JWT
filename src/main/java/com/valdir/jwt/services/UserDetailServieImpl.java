package com.valdir.jwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.valdir.jwt.domain.Cliente;
import com.valdir.jwt.repositories.ClienteRepository;
import com.valdir.jwt.security.UserSS;

/**
 * 
 * A classe abaixo implementa a interface UserDetailsService do SpringSecurity
 * assim podemos sobreescrevber o metodo loadUserByUsername() com a nossa lógica
 * de negócio e retirnar um UserSS que também é uma classe que implementa a
 * interface UserDetail do SpringSecurity adotando as boas práticas
 * 
 */
@Service
public class UserDetailServieImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Cliente cli = repository.findByEmail(email);

		if (cli == null)
			throw new UsernameNotFoundException("Usuário não encontrado! E-mail: " + email);

		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
