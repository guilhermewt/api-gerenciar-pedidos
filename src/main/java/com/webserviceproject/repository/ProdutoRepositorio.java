package com.webserviceproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webserviceproject.entities.Produto;

@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{

}
