package com.valdir.jwt.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valdir.jwt.enums.Perfil;

@Entity
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;

	/**
	 * 
	 * Adicionando a anotação @Jsonignore para que a senha não seja mostrada na
	 * recuperação de um cliente via endpoint
	 * 
	 */
	@JsonIgnore
	private String senha;

	/**
	 * O usuario pode ter perfil de admin e cliente A anotação (FetchType.EAGER) é
	 * usada para garantir que toda vez que um cliente for buscado na base de dados
	 * seus perfis também sejam buscados
	 * 
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();

	@Cascade(CascadeType.ALL)
	@OneToMany(mappedBy = "cliente")
	private List<Categoria> categorias = new ArrayList<>();

	public Cliente() {
		super();

		// Todo usuario já é instanciado com perfil de cliente
		addPerfis(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;

		// Todo usuario já é instanciado com perfil de cliente
		addPerfis(Perfil.CLIENTE);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	/**
	 * Recebemos um Perfil e adicionamos ao set de Integers do usuario
	 * 
	 */
	public void addPerfis(Perfil perfil) {
		perfis.add(perfil.getCodigo());
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
