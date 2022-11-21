package com.webserviceproject.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Usuario;
import com.webserviceproject.repository.UsuarioRepositorio;
import com.webserviceproject.services.exceptions.DataBaseException;
import com.webserviceproject.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepositorio repositorio;

	public List<Usuario> findAll() {
		return repositorio.findAll();
	}

	public Usuario findById(long id) {
		Optional<Usuario> usuario = repositorio.findById(id);
		return usuario.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Usuario insert(Usuario obj) {
		return repositorio.save(obj);
	}

	public void delete(long id) {
		try {
			repositorio.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new DataBaseException(e.getMessage());
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	//EntityNotFoundException,

	public Usuario Update(Usuario atualizarUsuario, long id) {
		try {
			Usuario usuarioDoBanco = repositorio.findById(id).get();
			updateData(usuarioDoBanco, atualizarUsuario);
			return repositorio.save(usuarioDoBanco);
		} catch (EntityNotFoundException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	private void updateData(Usuario usuarioDoBanco, Usuario atualizarUsuario) {
		usuarioDoBanco.setNome(atualizarUsuario.getNome());
		usuarioDoBanco.setEmail(atualizarUsuario.getEmail());
		usuarioDoBanco.setTelefone(atualizarUsuario.getTelefone());
		usuarioDoBanco.setSenha(atualizarUsuario.getSenha());

	}
}
