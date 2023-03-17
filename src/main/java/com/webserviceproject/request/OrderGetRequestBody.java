package com.webserviceproject.request;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.OrderItems;
import com.webserviceproject.entities.enums.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderGetRequestBody {
	
	private Long id;
	@NotNull(message = "the moment cannot be empty")
	@Schema(description = "moment of order")
	private Instant moment;
	
	@NotNull(message = "orderStatus cannot be null")
	@Schema(description = "status of the order",example="WAITING_PAYMENT, PAID, SHIPPED, DELIVERED, CANCELED")
	private Integer orderStatus;
	
	private Set<OrderItems> items = new HashSet<>();

	public OrderGetRequestBody(Long id,Instant moment, OrderStatus orderStatus) {
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
	
public static Page<OrderGetRequestBody> toOrderGetRequestBodyPage(Page<Order> order) {
		
		Page<OrderGetRequestBody> dtoPage = order.map(new Function<Order, OrderGetRequestBody>() {
			
			@Override
			public OrderGetRequestBody apply(Order order) {
				OrderGetRequestBody dto = new OrderGetRequestBody();
				dto.setId(order.getId());
				dto.setMoment(order.getMoment());
				dto.setOrderStatus(order.getOrderStatus());
				dto.setItems(order.getItems());
				return dto;
			}
		});
		
		return dtoPage;
	}
}
