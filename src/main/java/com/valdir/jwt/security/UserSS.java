package com.valdir.jwt.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.valdir.jwt.enums.Perfil;

/**
 * 
 * A classe se chama UserSS para Abreviar UserSpringSecurity
 * 
 */
public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {
		super();
	}

	public UserSS(Integer id, String email, String senha, Set<Perfil> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;

		/**
		 * Como eu mudei no construtor Collection<? extends GrantedAuthority> para
		 * receber um Set<Perfil> eu terei que converter este Set para o tipo
		 * GrantedAuthority do SpringSecurity uma vez que nós pegamos a descrição do
		 * perfil
		 */
		this.authorities = authorities.stream().map(obj -> new SimpleGrantedAuthority(obj.getDescricao())).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	/**
	 * Retorna as autoridades concedidas ao usuário.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	/**
	 * Indica se o usuário está bloqueado ou expirada
	 */
	@Override
	public boolean isAccountNonExpired() {
		/*** Por padrão vou retornar que a conta não está expirada ***/
		return true;
	}

	/**
	 * Indica se o usuário está bloqueado ou desbloqueado
	 */
	@Override
	public boolean isAccountNonLocked() {
		/*** Por padrão vou retornar que a conta não está bloqueada ***/
		return true;
	}

	/**
	 * Indica se as credenciais do usuário (senha) expiraram.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		/*** Por padrão vou retornar que a senha não está bloqueada ***/
		return false;
	}

	/**
	 * Indica se o usuário está habilitado ou desabilitado.
	 */
	@Override
	public boolean isEnabled() {
		/*** Por padrão vou retornar que o usuário está habilitado ***/
		return true;
	}

}
