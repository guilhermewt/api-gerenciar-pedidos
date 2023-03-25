package com.webserviceproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webserviceproject.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
	
	Optional<Payment> findByOrderId(long id);
}
