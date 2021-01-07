package com.valdir.jwt.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.valdir.jwt.security.JWTAuthenticationFilter;

/**
 * 
 * Herdando da classe WebsecurityConfigureAdapter para aproveitar as vantagens
 * de configurações de segurança da web fornecidas pelo Spring Security
 * Permitindo ajustar a estrutura de acordo com nossas necessidades
 * 
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;

	/**
	 * 
	 * A interface UserDetailsService é usada para recuperar dados relacionados ao
	 * usuário. Ele possui um método denominado loadUserByUsername() que pode ser
	 * substituído para personalizar o processo de localização do usuário.
	 * 
	 * O Spring possui a capacidade de buscar uma implementação da interface
	 * UserDetailService por conta própria no nosso projeto que no caso é o
	 * UserDetailServiceImpl, assim podemos usar o metodo loadUserByUsername
	 * sobreescrito com nossa lógica
	 * 
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/*** O vetor de String abaixo será os caminhos publicos para todo usuário ***/
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

	/***
	 * O vetor abaixo será para endpoints que serão apenas recuperados por qualquer
	 * usuário, logado ou não.
	 ***/
	private static final String[] PUBLIC_MATCHERS_GET = { "/categorias/**", "/produtos/**" };

	/**
	 * Metodo que irá criptografar a senha do usuário
	 *
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 
	 * Método onde vamos definir quais recursos são públicos e quais recurso serão
	 * protegidos Também foi configurado o suporte de CORS (Cross-Origin Resource)
	 * em http.cors() e adicionamos um filtro personalizado de segurança
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/***
		 * Verificando se o perfil ativo é o de teste e caso o Profile ativo estiver com
		 * o perfil de teste ativo o acesso ao H2 Database vai ser liberado
		 ***/
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		/*** Desabilitando o csrf e ativando @Bean cors definido abaixo ***/
		http.cors().and().csrf().disable();

		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().anyRequest().authenticated();
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));

		/*** Assegurando que a aplicação não vai criar sessão de usuário ***/
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	/**
	 *
	 * Metodo para prtmitirmos ou restringir nosso suporte CORS. Podemos assim liberar
	 * acessos a nossa API de várias fontes no nosso caso abaixo eu libeirei acesso
	 * de qualquer fonte pois por padrão ao habilitar o Spring Security o mesmo já
	 * bloqueia os acessos de Cross-Origin
	 *
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
