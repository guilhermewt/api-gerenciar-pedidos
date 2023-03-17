package com.webserviceproject.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.webserviceproject.controllers.OrderController;
import com.webserviceproject.entities.Order;
import com.webserviceproject.request.OrderGetRequestBody;
import com.webserviceproject.request.OrderPostRequestBody;
import com.webserviceproject.services.OrderService;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.util.OrderCreator;
import com.webserviceproject.util.ProductCreator;

@ExtendWith(SpringExtension.class)
public class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private OrderService orderServiceMock;

	@Mock
	private GetAuthenticatedUser getAuthenticatedUser;

	@BeforeEach
	void setUp() {
		BDDMockito.when(orderServiceMock.findAllNonPageable())
				.thenReturn(List.of(OrderCreator.createOrder()));
		PageImpl<Order> userPage = new PageImpl<>(List.of(OrderCreator.createOrder()));

		BDDMockito.when(orderServiceMock.findAllPageable(ArgumentMatchers.any(PageRequest.class)))
				.thenReturn(userPage);

		BDDMockito.when(orderServiceMock.findByIdOrElseThrowNewBadRequestException(ArgumentMatchers.anyLong()))
				.thenReturn(OrderCreator.createOrder());

		BDDMockito
				.when(orderServiceMock
						.save(ArgumentMatchers.any(OrderPostRequestBody.class),ArgumentMatchers.anyLong(),ArgumentMatchers.anyInt()))
				.thenReturn(OrderCreator.createOrder());

		BDDMockito.doNothing().when(orderServiceMock).delete(ArgumentMatchers.anyLong());
		
		BDDMockito.when(orderServiceMock.findByIdOrElseThrowNewBadRequestException(ArgumentMatchers.anyLong()))
		.thenReturn(OrderCreator.createOrder());

	}

	@Test
	@DisplayName("findAll return list of order whenSuccessful")
	void findAll_returnListOfOrder_WhenSucceful() {
		OrderGetRequestBody orderSaved = OrderCreator.createOrderGetRequestBodyCreator();

		List<OrderGetRequestBody> orderEntity = this.orderController.findAll().getBody();

		Assertions.assertThat(orderEntity).isNotNull();
		Assertions.assertThat(orderEntity.get(0).getId()).isNotNull();
		Assertions.assertThat(orderEntity.get(0)).isEqualTo(orderSaved);
	}
	
	@Test
	@DisplayName("findAll return List Of Object Inside Page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		OrderGetRequestBody orderSaved = OrderCreator.createOrderGetRequestBodyCreator();

		Page<OrderGetRequestBody> userPage = orderController.findAllPageable(PageRequest.of(0, 1)).getBody();

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(orderSaved);
	}

	@Test
	@DisplayName("findById return order whenSuccessful")
	void findById_ReturnOrder_whenSuccessful() {
		OrderGetRequestBody orderSaved = OrderCreator.createOrderGetRequestBodyCreator();

		OrderGetRequestBody order = this.orderController.findById(orderSaved.getId()).getBody();

		Assertions.assertThat(order).isNotNull();
		Assertions.assertThat(order.getId()).isNotNull();
		Assertions.assertThat(order).isEqualTo(orderSaved);
	}

	@Test
	@DisplayName("save return order whenSuccessful")
	void save_ReturnOrder_whenSuccessful() {
		OrderGetRequestBody orderSaved = OrderCreator.createOrderGetRequestBodyCreator();

		OrderGetRequestBody order = this.orderController
				.save(OrderCreator.createOrderPostRequestBodyCreator(),ProductCreator.createProduct().getId(),1).getBody();

		Assertions.assertThat(order).isNotNull();
		Assertions.assertThat(order.getId()).isNotNull();
		Assertions.assertThat(order).isEqualTo(orderSaved);
	}

	@Test
	@DisplayName("delete removes order whenSuccessful")
	void delete_RemovesOrder_whenSuccessful() {
		Assertions.assertThatCode(() -> this.orderController.delete(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update replace order whenSuccessful")
	void update_ReplaveOrder_whenSuccessful() {
		Assertions.assertThatCode(
				() -> this.orderController.update(OrderCreator.createOrderPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
	
}
