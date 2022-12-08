package com.webserviceproject.request;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.webserviceproject.entities.ItemsDePedido;
import com.webserviceproject.entities.enums.OrderStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PedidoPutRequestBody {
	
	private long id;
	@NotNull(message = "the moment cannot be empty")
	private Instant moment;
	@NotEmpty(message = "orderStatus cannot be empty")
	private Integer orderStatus;

	private Set<ItemsDePedido> items = new HashSet<>();

	public PedidoPutRequestBody(long id,Instant moment, OrderStatus orderStatus) {
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
}
