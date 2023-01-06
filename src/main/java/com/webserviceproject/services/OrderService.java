package com.webserviceproject.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Order;
import com.webserviceproject.entities.OrderItems;
import com.webserviceproject.entities.Product;
import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.mapper.OrderMapper;
import com.webserviceproject.repository.OrderItemsRepository;
import com.webserviceproject.repository.OrderRepository;
import com.webserviceproject.request.OrderPostRequestBody;
import com.webserviceproject.request.OrderPutRequestBody;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final OrderItemsRepository orderItemsRepository;
	private final ProductService productService;
	private final GetAuthenticatedUser authenthicatedUser;
	
	public List<Order> findAllNonPageable(){
		return orderRepository.findByUserDomainId(authenthicatedUser.userAuthenticated().getId());
	}
	
	public Page<Order> findAllPageable(Pageable pageable){
		return orderRepository.findByUserDomainId(authenthicatedUser.userAuthenticated().getId(),pageable);
	}
	
	public Order findByIdOrElseThrowNewBadRequestException(long orderId) {
		return orderRepository.findAuthenticatedUserDomainOrderById(orderId, authenthicatedUser.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("order not found"));
	}
	
	public Order save(OrderPostRequestBody orderPostRequestBody,long productId,int quantityProduct) {
		Order order = OrderMapper.INSTANCE.toOrder(orderPostRequestBody);
		
		UserDomain userDomain = authenthicatedUser.userAuthenticated();
		
		order.setUserDomain(userDomain);
		
		Product product = productService.findByIdOrElseThrowBadRequestException(productId);
		
		OrderItems items = new OrderItems(product, order, quantityProduct, product.getPrice());
		orderItemsRepository.save(items);
        order = orderRepository.save(order);	
		
		return order;
	}
	
	public void update(OrderPutRequestBody orderPutRequestBody) {
		Order orderSave = findByIdOrElseThrowNewBadRequestException(orderPutRequestBody.getId());
		checkIfOrderIsNotPaid(orderSave);
		
		orderRepository.save(OrderMapper.INSTANCE.updateOrder(orderPutRequestBody, orderSave));		
	}
	
	@Transactional
	public void delete(long orderId) {
		try {
			
		orderRepository.delete(findByIdOrElseThrowNewBadRequestException(orderId));
		} 
		catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public void checkIfOrderIsNotPaid(Order order) {
		if(order.getOrderStatus().name() == "PAID") {
			throw new BadRequestException("this order is already paid!");
		}
	}
}
