package com.webserviceproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webserviceproject.entities.UserDomain;

@Repository
public interface UserDomainRepository extends JpaRepository<UserDomain, Long>{
	
	Optional<UserDomain> findByUsername(String username);
}
