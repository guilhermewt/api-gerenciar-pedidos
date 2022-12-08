package com.webserviceproject.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webserviceproject.entities.enums.OrderStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pedido")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id"})
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "the moment cannot be empty")
	private Instant moment;
	@NotEmpty(message = "orderStatus cannot be empty")
	private Integer orderStatus;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@JsonIgnore
	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
	private Pagamento pagamento;

	@OneToMany(mappedBy = "id.pedido",cascade = CascadeType.ALL)
	private Set<ItemsDePedido> items = new HashSet<>();

	public Pedido(Long id, Instant moment, OrderStatus orderStatus) {
		super();
		this.id = id;
		this.moment = moment;
		setOrderStatus(orderStatus);
	}

	public OrderStatus getOrderStatus() {
		return OrderStatus.valueOf(orderStatus);
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		if (orderStatus != null) {
			this.orderStatus = orderStatus.getCode();
		}
	}

	public Double getTotal() {
		double soma = 0;
		for (ItemsDePedido x : items) {
			soma += x.getSubTotal();
		}
		return soma;
	}

}
