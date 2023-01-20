package com.webserviceproject.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.enums.OrderStatus;
import com.webserviceproject.util.OrderCreator;

@DataJpaTest
@DisplayName("test for order repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public static Order createOrder() {
		Order order = new Order(1l, Instant.parse("2022-12-31T00:00:00Z"), OrderStatus.WAITING_PAYMENT);
		return order;
	}
	
	@Test
	@DisplayName("find all order by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		Order orderToBeSaved = orderRepository.save(createOrder());
		Page<Order> orderSaved = this.orderRepository.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(orderSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(orderSaved.toList().get(0)).isEqualTo(orderToBeSaved);
	}
	
	@Test
	@DisplayName("findall return all order whenSuccessful")
	void findAll_returnAllOrder_whenSuccessful() {
		Order orderToBeSaved = orderRepository.save(createOrder());
		List<Order> orderList = this.orderRepository.findAll();
		
		Assertions.assertThat(orderList).isNotNull();
		Assertions.assertThat(orderList.get(0).getId()).isNotNull();
		Assertions.assertThat(orderList.get(0)).isEqualTo(orderToBeSaved);
	}
	
	@Test
	@DisplayName("findById return order whenSuccessful")
	void findByid_returnorder_whenSuccessful() {			
		Order orderToBeSaved = orderRepository.save(createOrder());
		Order orderSaved = this.orderRepository.findById(orderToBeSaved.getId()).get();
		
		Assertions.assertThat(orderSaved).isNotNull();
		Assertions.assertThat(orderSaved.getId()).isNotNull();
		Assertions.assertThat(orderSaved).isEqualTo(orderToBeSaved);
	}
			
	@Test
	@DisplayName("save return order whenSuccessful")
	void save_returnorder_whenSuccessful() {	
		Order orderToBeSaved = orderRepository.save(createOrder());
		Order orderSaved = this.orderRepository.save(orderToBeSaved);
		
		Assertions.assertThat(orderSaved).isNotNull();
		Assertions.assertThat(orderSaved.getId()).isNotNull();
		Assertions.assertThat(orderSaved).isEqualTo(orderToBeSaved);
	}
	
	@Test
	@DisplayName("delete removes order whenSuccessful")
	void delete_removesorder_whenSuccessful() {
		Order orderToBeSaved = orderRepository.save(createOrder());
	
	    this.orderRepository.delete(orderToBeSaved);
	    
	    Optional<Order> orderDeleted = this.orderRepository.findById(orderToBeSaved.getId());
	    
	    Assertions.assertThat(orderDeleted).isEmpty();
	}
	
	@Test
	@DisplayName("update replace order whenSuccessful")
	void update_replaceOrder_whenSuccessful() {
		this.orderRepository.save(createOrder());
			
		Order orderToBeUpdate = OrderCreator.createOrderToBeUpdate();
	
	    Order orderUpdated = this.orderRepository.save(orderToBeUpdate);
	    
	    Assertions.assertThat(orderUpdated).isNotNull();
	    Assertions.assertThat(orderUpdated.getId()).isNotNull();
	    Assertions.assertThat(orderUpdated).isEqualTo(orderToBeUpdate);
	}
	
	@Test
	@DisplayName("save  throw Contration Violation Exception when order name is empty")
	void save_throwConstrationViolationException_whenOrderNameIsEmpty() {
	
		Order order = new Order();
		
		Assertions.assertThatThrownBy(() -> this.orderRepository.save(order))
		.isInstanceOf(ConstraintViolationException.class);
	}
	
}
