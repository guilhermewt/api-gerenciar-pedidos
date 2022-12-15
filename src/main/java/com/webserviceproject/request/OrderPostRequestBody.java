package com.webserviceproject.request;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.webserviceproject.entities.OrderItems;
import com.webserviceproject.entities.Payment;
import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.entities.enums.OrderStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderPostRequestBody {
	
	@NotNull(message = "the moment cannot be empty")
	private Instant moment;
	@NotNull(message = "orderStatus cannot be null")
	private Integer orderStatus;

	private UserDomain userDomain;
	private Payment payment;
	private Set<OrderItems> items = new HashSet<>();

	public OrderPostRequestBody(Instant moment, OrderStatus orderStatus) {
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
