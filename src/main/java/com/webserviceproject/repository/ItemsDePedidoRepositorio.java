package com.webserviceproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webserviceproject.entities.ItemsDePedido;

@Repository
public interface ItemsDePedidoRepositorio extends JpaRepository<ItemsDePedido, Long>{

}
