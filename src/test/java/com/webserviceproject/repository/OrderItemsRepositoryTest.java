package com.webserviceproject.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import com.webserviceproject.entities.OrderItems;
import com.webserviceproject.util.OrderCreator;
import com.webserviceproject.util.OrderItemsCreator;
import com.webserviceproject.util.ProductCreator;

@DataJpaTest
@DisplayName("test for orderItems repository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderItemsRepositoryTest {
	
	@Autowired
	private OrderItemsRepository orderItemsRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
		
	@Test
	@DisplayName("find all orderItems by id return list of object inside page whensuccessful")
	void findAll_returnListOfObjectInsidePage_whenSuccessful() {
		productRepository.save(ProductCreator.createProduct());
		orderRepository.save(OrderCreator.createOrder());
		
	
		OrderItems orderItemsToBeSaved = orderItemsRepository.save(OrderItemsCreator.createOrderItems());
		Page<OrderItems> orderItemsSaved = this.orderItemsRepository.findAll(PageRequest.of(0, 5));
		
		Assertions.assertThat(orderItemsSaved).isNotNull().isNotEmpty();
		Assertions.assertThat(orderItemsSaved.toList().get(0)).isEqualTo(orderItemsToBeSaved);
	}
	
	@Test
	@DisplayName("findall return all orderItems whenSuccessful")
	void findAll_returnAllOrderItems_whenSuccessful() {
		productRepository.save(ProductCreator.createProduct());
		orderRepository.save(OrderCreator.createOrder());
		
		OrderItems orderItemsToBeSaved = orderItemsRepository.save(OrderItemsCreator.createOrderItems());
		List<OrderItems> orderItemsList = this.orderItemsRepository.findAll();
		
		Assertions.assertThat(orderItemsList).isNotNull();
		Assertions.assertThat(orderItemsList.get(0).getId()).isNotNull();
		Assertions.assertThat(orderItemsList.get(0)).isEqualTo(orderItemsToBeSaved);
	}
				
	@Test
	@DisplayName("save return orderItems whenSuccessful")
	void save_returnorderItems_whenSuccessful() {	
		OrderItems orderItemsToBeSaved = orderItemsRepository.save(OrderItemsCreator.createOrderItems());
		OrderItems orderItemsSaved = this.orderItemsRepository.save(orderItemsToBeSaved);
		
		Assertions.assertThat(orderItemsSaved).isNotNull();
		Assertions.assertThat(orderItemsSaved.getId()).isNotNull();
		Assertions.assertThat(orderItemsSaved).isEqualTo(orderItemsToBeSaved);
	}
	
}
