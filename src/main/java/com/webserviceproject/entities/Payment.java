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
import lombok.ToString;

@Entity
@Table(name = "tb_payment")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id"})
@ToString(exclude = "order")
public class Payment implements Serializable{
	/*
	 * repository
	 * service:recebe id do pedido e payment associa e salva
	 * controle: metodo salva
	 * */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "the payment moment cannot be empty")
	private Instant moment;
	
	@OneToOne
	@MapsId
	private Order order;

	public Payment(Long id, Instant moment) {
		super();
		this.id = id;
		this.moment = moment;
	}
	
}
