package com.valdir.jwt.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.valdir.jwt.config.JWTUtil;

/**
 * Será criado um filtro para analizar o token que vamos receber como parametro
 * no construtor da superclasse e analizar se o mesmo é válido. O usuário será
 * extraido do token e será r5ealizado uma busca na base de dados se o usuário
 * realmente existe
 * 
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;

	/*
	 * O detailsService é quem vai realizar a busca do usuário pelo email
	 */
	private UserDetailsService userDetailsService;

	/*
	 * Esse é o metodo que intercepta a requisição e analiza se o mesmo está
	 * autorizado
	 */
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService detailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = detailsService;
	}

	/**
	 * 
	 * O metodo abaixo vai interceptar a requisição
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/*
		 * O 1º passo é pegar o valor que veio no cabeçalho da requisição (Bearer +
		 * token) e para isso vamos usar o argumento request recebido como parametro no
		 * metodo. Para pegar o valor que queremos no Header da requisição é simples
		 * podemos apenas usar o request.getHeader(Passar o nome do parametro que
		 * queremos do header)
		 */
		String header = request.getHeader("Authorization");

		/*
		 * Primeiro validaremos se o header é diferente de nulo Depois verificamos se o
		 * nosso header começa com Bearer
		 */
		if (header != null && header.startsWith("Bearer ")) {
			/*
			 * Será criado um metodo que irá passar como parametro um token e o metodo
			 * getAuthenticationManager e ele vai retornar um objeto do tipo
			 * UsernamePasswordAuthenticationToken que é do Spring Security caso o token
			 * seja válido, senão vai retornar nulo
			 */
			UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationManager(request,
					header.substring(7));

			/*
			 * Se o token não vier nulo significa que ele foi autorizado e vamos liberar a
			 * authenticação para o usuário
			 */
			if (authenticationToken != null) {
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		/*
		 * Se tudo der certo nós chamamos o chain para continuar a execução da
		 * requisição
		 */
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationManager(HttpServletRequest request, String token) {
		/*
		 * Se o token for válido o usuário está authorizado
		 */
		if(this.jwtUtil.tokenValido(token)) {
			String username = this.jwtUtil.getUsername(token);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		}
	}

}
