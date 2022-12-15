package com.webserviceproject.entities.pk;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.Product;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class orderItemsPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private Order order;

}
