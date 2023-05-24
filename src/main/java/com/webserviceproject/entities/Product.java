package com.webserviceproject.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_product")
@NoArgsConstructor
@Data
public class Product implements Serializable{
//	english:config,controller,entities faltam os(rolemodel,usuario)
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "the product name cannot be empty")
	private String name;
	
	@NotEmpty(message = "the product description cannot be empty")
	private String description;
	@NotNull(message = "the product price cannot be empty")
	private Double price;
	private String imgUrl;
	
	
	@ManyToMany
	@JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> category = new HashSet<>();
	
	@OneToMany(mappedBy = "id.order")
	@JsonIgnore
	private Set<OrderItems> items = new HashSet<>();

	public Product(Long id, String name, String description, Double price, String imgUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
	
	@JsonIgnore
	public Set<Order> getOrder(){
		Set<Order> orderList = new HashSet<>();
		for(OrderItems x : items) {
			orderList.add(x.getOrder());
		}
		
		return orderList;
	}

}
