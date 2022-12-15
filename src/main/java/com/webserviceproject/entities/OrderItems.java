package com.webserviceproject.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webserviceproject.entities.pk.orderItemsPK;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_order_items")
@Data
@NoArgsConstructor
public class OrderItems implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private orderItemsPK id = new orderItemsPK();
	
	private Integer quantity;
	private Double price;

	public OrderItems(Product product,Order order, Integer quantity, Double price) {
		super();
		this.quantity = quantity;
		this.price = price;
		id.setProduct(product);
		id.setOrder(order);
	}
	
	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(Product product) {
		id.setProduct(product);
	}
	
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(Order order) {
		id.setOrder(order);
	}

	public Double getSubTotal() {
		return quantity * price;
	}

}
