package com.webserviceproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webserviceproject.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
