package com.webserviceproject.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webserviceproject.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByUserDomainId(long userDomainId);
	
	Page<Order> findByUserDomainId(long userDomainId,Pageable pageable);
	
	@Query("select u from Order u where u.id = ?1 and u.userDomain.id = ?2")
	Optional<Order> findAuthenticatedUserDomainOrderById(long OrderId,long userDomainId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Order u where u.id = ?1 and u.userDomain.id = ?2")
    void deleteAuthenticatedUserDomainOrderById(long orderId,long userDomainId);

}
