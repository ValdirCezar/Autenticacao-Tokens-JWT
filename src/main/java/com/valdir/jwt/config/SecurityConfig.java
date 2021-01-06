package com.valdir.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.valdir.jwt.enums.Perfil;

/*
 * Herdando da classe WebsecurityConfigureAdapter
 * 
 * */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Metodo que irá criptografar a senha do usuário
	 *
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Método que será usado para configurar a authenticação do usuário
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// Criando uma autenticação em memória para teste
		auth.inMemoryAuthentication()
				.passwordEncoder(passwordEncoder())
				.withUser("valdir")
				.password(passwordEncoder().encode("123"))
				.roles(Perfil.ADMIN.toString());
	}

	/**
	 * Método que será usado para tratar as requisições e validar as roles e
	 * authorities
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	}

}
