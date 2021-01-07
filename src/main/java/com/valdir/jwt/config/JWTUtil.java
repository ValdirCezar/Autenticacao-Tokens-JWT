package com.valdir.jwt.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Adicionando a anotação @Component para podermos injetar novas instancias
 * dessa classe usando o @Autowired em outras partes do projeto
 * 
 * Essa classe nós vamos usar para gerar um token Para isso iremos usar o pacote
 * JWT
 * 
 */
@Component
public class JWTUtil {

	/**
	 * O macete abaixo irá pegar o valor de jwt.secretkey em aplication.properties e
	 * atibuir a nossa variável secretkey, pois precisaremos dela para gerar um
	 * token posteriormente
	 */
	@Value("${jwt.secretkey}")
	private String secretkey;

	/**
	 * O macete abaixo irá pegar o valor de jwt.expiration em aplication.properties
	 * e atibuir a nossa variável expiration, pois também precisaremos dela para
	 * gerar um token
	 */
	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * O metodo abaixo irá gerar e retornar um novo token builder() é o metodo que
	 * gera o token
	 * 
	 * setSubject() é o usuário que veio como argumento no método
	 * 
	 * setExpiration() é a expiração do token ou seja, ele vai durar o tempo da hora
	 * atual + o tempo setado no expiration em aplication.properties que foi de
	 * 60000 milisegundos (1 minuto), sendo assim o token vai durar apenas 1 minuto
	 * que é para realizar os testes e posteriormente podemos aumentar esse tempo
	 * 
	 * signWith() vou usar pra dizer como eu irei assinar meu token, informando o
	 * algoritmo utilizado e a chave secreta que criamos acima recebendo o parametro
	 * no arquivo properties
	 */
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, this.secretkey.getBytes()).compact();
	}

}
