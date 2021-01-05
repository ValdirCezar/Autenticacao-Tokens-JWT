package com.valdir.jwt.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Campo NOME é mandatório")
	@Length(min = 3, max = 100, message = "O campo NOME deve ter entre 3 e 100 caracteres")
	private String nome;

	@NotEmpty(message = "Campo PREÇO é mandatório")
	private Double preco;

	public ProdutoDTO() {
		super();
	}

	public ProdutoDTO(Integer id,
			@NotEmpty(message = "Campo NOME é mandatório") @Length(min = 3, max = 100, message = "O campo NOME deve ter entre 3 e 100 caracteres") String nome,
			@NotEmpty(message = "Campo PREÇO é mandatório") Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
