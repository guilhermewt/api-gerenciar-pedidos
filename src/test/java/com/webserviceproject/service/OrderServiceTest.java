package com.webserviceproject.service;

import java.util.List;
import java.util.Optional;

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

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.OrderItems;
import com.webserviceproject.repository.OrderItemsRepository;
import com.webserviceproject.repository.OrderRepository;
import com.webserviceproject.services.CategoryService;
import com.webserviceproject.services.OrderService;
import com.webserviceproject.services.ProductService;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.util.CategoryCreator;
import com.webserviceproject.util.OrderCreator;
import com.webserviceproject.util.OrderItemsCreator;
import com.webserviceproject.util.OrderPostRequestBodyCreator;
import com.webserviceproject.util.OrderPutRequestBodyCreator;
import com.webserviceproject.util.ProductCreator;
import com.webserviceproject.util.UserDomainCreator;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {
	
	@InjectMocks
	private OrderService orderService;
	
	@Mock
	private OrderRepository orderRepositoryMock;
	
	@Mock
	private CategoryService categoryRepositoryMock;
	
	@Mock
	private ProductService productRepositoryMock;
	
	@Mock
	private GetAuthenticatedUser getAuthenticatedUser;
	
	@Mock
	private OrderItemsRepository orderItemsRepository;

	@BeforeEach
	void setUp() {
		BDDMockito.when(orderRepositoryMock.findByUserDomainId(ArgumentMatchers.anyLong())).thenReturn(List.of(OrderCreator.createOrder()));
		
		PageImpl<Order> userPage = new PageImpl<>(List.of(OrderCreator.createOrder()));
		BDDMockito.when(orderRepositoryMock.findByUserDomainId(ArgumentMatchers.anyLong(),ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

		BDDMockito.when(orderRepositoryMock.findAuthenticatedUserDomainOrderById(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(OrderCreator.createOrder()));
		        
		BDDMockito.when(orderRepositoryMock.save(ArgumentMatchers.any(Order.class))).thenReturn(OrderCreator.createOrder());
		
		BDDMockito.doNothing().when(orderRepositoryMock).delete(ArgumentMatchers.any(Order.class));
		
		BDDMockito.when(categoryRepositoryMock.findByIdOrElseThrowBadRequestException(ArgumentMatchers.anyLong())).thenReturn(CategoryCreator.createCategory());

		BDDMockito.when(productRepositoryMock.findByIdOrElseThrowBadRequestException(ArgumentMatchers.anyLong())).thenReturn(ProductCreator.createProduct());
		
		BDDMockito.when(getAuthenticatedUser.userAuthenticated()).thenReturn(UserDomainCreator.createUserDomain());
		
		BDDMockito.when(orderItemsRepository.save(ArgumentMatchers.any(OrderItems.class))).thenReturn(OrderItemsCreator.createOrderItems());
	}
	
	@Test
	@DisplayName("find all return list of order whenSuccessfull")
	void findAll_returnListOfOrder_whenSuccessful() {
		Order listOrderToComparable = OrderCreator.createOrder();
		List<Order> listOrder = orderService.findAllNonPageable();
		
		Assertions.assertThat(listOrder).isNotNull();
		Assertions.assertThat(listOrder.get(0).getId()).isNotNull();
		Assertions.assertThat(listOrder.get(0)).isEqualTo(listOrderToComparable);	
	}
	
	@Test
	@DisplayName("find all order return list of object inside page whenSuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Order orderSaved = OrderCreator.createOrder();

		Page<Order> userPage = orderService.findAllPageable(PageRequest.of(0, 1));

		Assertions.assertThat(userPage).isNotNull();
		Assertions.assertThat(userPage.toList().get(0).getId()).isNotNull();
		Assertions.assertThat(userPage.toList().get(0)).isEqualTo(orderSaved);
	}
	
	@Test
	@DisplayName("findById return order whenSuccessful")
	void findById_Returnorder_whenSuccessful() {
		Order orderSaved = OrderCreator.createOrder();

		Order order = this.orderService.findByIdOrElseThrowNewBadRequestException(orderSaved.getId());

		Assertions.assertThat(order).isNotNull();
		Assertions.assertThat(order.getId()).isNotNull();
		Assertions.assertThat(order).isEqualTo(orderSaved);
	}

	@Test
	@DisplayName("save order Return order whenSuccessful")
	void save_ReturnOrder_whenSuccessful() {
		Order orderSaved = OrderCreator.createOrder();

		Order order = this.orderService.save(OrderPostRequestBodyCreator.createOrderPostRequestBodyCreator(), 1l, 1);

		Assertions.assertThat(order).isNotNull();
		Assertions.assertThat(order.getId()).isNotNull();
		Assertions.assertThat(order).isEqualTo(orderSaved);
	}
	
	@Test
	@DisplayName("delete Removes order whenSuccessful")
	void delete_RemovesOrder_whenSuccessful() {
		Assertions.assertThatCode(() -> this.orderService.delete(1l)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("update Replace order whenSuccessful")
	void update_Replaveorder_whenSuccessful() {
		Assertions
				.assertThatCode(
						() -> this.orderService.update(OrderPutRequestBodyCreator.createOrderPutRequestBodyCreator()))
				.doesNotThrowAnyException();
	}
}
