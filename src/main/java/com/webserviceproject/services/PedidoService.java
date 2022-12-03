package com.webserviceproject.services;

import java.util.List;

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
import com.webserviceproject.services.exceptions.DataBaseException;
import com.webserviceproject.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
	
	private final PedidoRepositorio pedidoRepositorio;
	private final ItemsDePedidoRepositorio itemsRepositorio;
	private final ProdutoService produtoService;
	private final UsuarioService usuarioService;
	
	
	public List<Pedido> findAll(){
		return pedidoRepositorio.findAll();
	}
	
	public Pedido findById(long id) {
		return pedidoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Pedido save(PedidoPostRequestBody pedidoPostRequestBody,long produtoId,int quantidadeProduto,long usuarioId) {
		Pedido pedido = PedidoMapper.INSTANCE.toPedido(pedidoPostRequestBody);
		
		Usuario usuario = usuarioService.findById(usuarioId);
		
		pedido.setUsuario(usuario);
		
		Produto produto = produtoService.findById(produtoId);
		
		ItemsDePedido items = new ItemsDePedido(produto, pedido, quantidadeProduto, produto.getPreco());
		
		pedidoRepositorio.save(pedido);
		itemsRepositorio.save(items);
		return  pedido;
	}
	
	public void update(PedidoPutRequestBody pedidoPostRequestBody) {
		Pedido pedidoSalvo = pedidoRepositorio.findById(pedidoPostRequestBody.getId()).get();
		verificarPedido(pedidoSalvo);
		
		pedidoRepositorio.save(PedidoMapper.INSTANCE.updatePedido(pedidoPostRequestBody, pedidoSalvo));
		
	}
	
	public void verificarPedido(Pedido pedido) {
		if(pedido.getOrderStatus().name() == "PAID") {
			throw new DataBaseException("this order is already paid!");
		}
	}
}
