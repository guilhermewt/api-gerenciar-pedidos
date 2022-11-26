package com.webserviceproject.request;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.webserviceproject.entities.ItemsDePedido;
import com.webserviceproject.entities.Pagamento;
import com.webserviceproject.entities.Usuario;
import com.webserviceproject.entities.enums.OrderStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PedidoPostRequestBody {
	
	
	private Instant moment;
	private Integer orderStatus;

	private Usuario usuario;
	private Pagamento pagamento;
	private Set<ItemsDePedido> items = new HashSet<>();

	public PedidoPostRequestBody(Instant moment, OrderStatus orderStatus) {
		super();
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
