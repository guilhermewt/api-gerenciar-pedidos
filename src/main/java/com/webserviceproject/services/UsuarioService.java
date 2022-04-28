package com.webserviceproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Usuario;
import com.webserviceproject.repository.UsuarioRepositorio;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	public List<Usuario> findAll(){
		return repositorio.findAll();
	}
	
	public Usuario findById(long id) {
		Optional<Usuario> usuario = repositorio.findById(id);
		return usuario.get();
	}
	
	public Usuario insert(Usuario obj) {
		return repositorio.save(obj);
	}
	
	public void delete(long id) {
		repositorio.deleteById(id);
	}
	
	public Usuario Update(Usuario atualizarUsuario,long id) {
		Usuario usuarioDoBanco = repositorio.getOne(id);
		updateData(usuarioDoBanco,atualizarUsuario);
		return repositorio.save(usuarioDoBanco);
	}

	private void updateData(Usuario usuarioDoBanco, Usuario atualizarUsuario) {
		usuarioDoBanco.setNome(atualizarUsuario.getNome());
		usuarioDoBanco.setEmail(atualizarUsuario.getEmail());
		usuarioDoBanco.setTelefone(atualizarUsuario.getTelefone());
		usuarioDoBanco.setSenha(atualizarUsuario.getSenha());
		
	}
}
