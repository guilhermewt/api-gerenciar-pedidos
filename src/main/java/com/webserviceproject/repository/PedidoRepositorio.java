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

import com.webserviceproject.entities.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long>{
	
	List<Pedido> findByUsuarioId(long UserDomainId);
	
	Page<Pedido> findByUsuarioId(long UserDomainId,Pageable pageable);
	
	@Query("select u from Pedido u where u.id = ?1 and u.usuario.id = ?2")
	Optional<Pedido> findAuthenticatedUserPedidoById(long idPedido,long usuarioId);
	
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Pedido u where u.id = ?1 and u.usuario.id = ?2")
    void deleteAuthenticatedUsuarioPedidoById(long idPedido,long usuarioId);

}
