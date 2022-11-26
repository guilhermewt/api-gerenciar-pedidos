package com.webserviceproject.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.webserviceproject.entities.Pedido;
import com.webserviceproject.entities.Produto;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class itemsDePedidoPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

}
