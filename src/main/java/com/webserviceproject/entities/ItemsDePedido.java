package com.webserviceproject.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webserviceproject.entities.pk.itemsDePedidoPK;

@Entity
@Table(name = "tb_itemsDePedido")
public class ItemsDePedido implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private itemsDePedidoPK id = new itemsDePedidoPK();
	
	private Integer quantidade;
	private Double preco;
	
	public ItemsDePedido() {
	}

	public ItemsDePedido(Produto produto,Pedido pedido, Integer quantidade, Double preco) {
		super();
		this.quantidade = quantidade;
		this.preco = preco;
		id.setProduto(produto);
		id.setPedido(pedido);
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}
	
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Double getSubTotal() {
		return quantidade * preco;
	}

}
