package com.webserviceproject.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.ItemsDePedido;
import com.webserviceproject.entities.Pedido;
import com.webserviceproject.entities.Produto;
import com.webserviceproject.entities.Usuario;
import com.webserviceproject.mapper.PedidoMapper;
import com.webserviceproject.repository.ItemsDePedidoRepositorio;
import com.webserviceproject.repository.PedidoRepositorio;
import com.webserviceproject.request.PedidoPostRequestBody;
import com.webserviceproject.request.PedidoPutRequestBody;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.services.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {
	
	private final PedidoRepositorio pedidoRepositorio;
	private final ItemsDePedidoRepositorio itemsRepositorio;
	private final ProdutoService produtoService;
	private final GetAuthenticatedUser authenthicatedUser;
	
	
	public List<Pedido> findAllNonPageable(){
		return pedidoRepositorio.findByUsuarioId(authenthicatedUser.userAuthenticated().getId());
	}
	
	public Page<Pedido> findAllPageable(Pageable pageable){
		return pedidoRepositorio.findByUsuarioId(authenthicatedUser.userAuthenticated().getId(),pageable);
	}
	
	public Pedido findById(long idPedido) {
		return pedidoRepositorio.findAuthenticatedUserPedidoById(idPedido, authenthicatedUser.userAuthenticated().getId())
				.orElseThrow(() -> new BadRequestException("pedido not found"));
	}
	
	public Pedido save(PedidoPostRequestBody pedidoPostRequestBody,long produtoId,int quantidadeProduto) {
		Pedido pedido = PedidoMapper.INSTANCE.toPedido(pedidoPostRequestBody);
		
		Usuario usuario = authenthicatedUser.userAuthenticated();
		
		pedido.setUsuario(usuario);
		
		Produto produto = produtoService.findById(produtoId);
		
		ItemsDePedido items = new ItemsDePedido(produto, pedido, quantidadeProduto, produto.getPreco());
		
		pedidoRepositorio.save(pedido);
		itemsRepositorio.save(items);
		return  pedido;
	}
	
	public void update(PedidoPutRequestBody pedidoPutRequestBody) {
		Pedido pedidoSalvo = findById(pedidoPutRequestBody.getId());
		checkIfOrderIsNotPaid(pedidoSalvo);
		
		pedidoRepositorio.save(PedidoMapper.INSTANCE.updatePedido(pedidoPutRequestBody, pedidoSalvo));		
	}
	
	@Transactional
	public void delete(long idPedido) {
		try {
			
		pedidoRepositorio.delete(findById(idPedido));
		} 
		catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public void checkIfOrderIsNotPaid(Pedido pedido) {
		if(pedido.getOrderStatus().name() == "PAID") {
			throw new BadRequestException("this order is already paid!");
		}
	}
}
