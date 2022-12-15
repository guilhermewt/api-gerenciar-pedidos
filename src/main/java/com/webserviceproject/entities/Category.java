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
@Table(name = "tb_category")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id","name"})
public class Category implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "the category name cannot be empty")
	private String name;
	
	@ManyToMany(mappedBy = "category")
	@JsonIgnore
	private Set<Product> Product = new HashSet<>();

	public Category(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
