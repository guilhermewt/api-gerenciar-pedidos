package com.webserviceproject.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_categoria")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id","nome"})
public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "the nome cannot be empty")
	private String nome;
	
	@ManyToMany(mappedBy = "categoria")
	@JsonIgnore
	private Set<Produto> Produto = new HashSet<>();

	public Categoria(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
}
