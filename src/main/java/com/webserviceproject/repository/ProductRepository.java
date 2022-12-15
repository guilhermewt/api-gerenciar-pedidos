package com.webserviceproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webserviceproject.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
