package com.webserviceproject.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pagamento")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id"})
public class Pagamento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "the moment cannot be empty")
	private Instant moment;
	
	@OneToOne
	@MapsId
	private Pedido pedido;

	public Pagamento(Long id, Instant moment, Pedido pedido) {
		super();
		this.id = id;
		this.moment = moment;
		this.pedido = pedido;
	}
	
}
