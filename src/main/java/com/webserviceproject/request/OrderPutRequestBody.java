package com.webserviceproject.request;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.webserviceproject.entities.enums.OrderStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderPutRequestBody {
	
	private long id;
	@NotNull(message = "the moment cannot be empty")
	private Instant moment;
	@NotNull(message = "orderStatus cannot be null")
	private Integer orderStatus;

	public OrderPutRequestBody(long id,Instant moment, OrderStatus orderStatus) {
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
