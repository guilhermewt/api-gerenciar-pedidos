package com.webserviceproject.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Usuario;
import com.webserviceproject.mapper.UsuarioMapper;
import com.webserviceproject.repository.UsuarioRepositorio;
import com.webserviceproject.request.UsuarioPostRequestBody;
import com.webserviceproject.request.UsuarioPutRequestBody;
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
		return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Usuario insert(UsuarioPostRequestBody usuarioPostRequestBody) {
		return repositorio.save(UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody));
	}

	public void delete(long id) {
		try {
			repositorio.delete(findById(id));
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	public void atualizarUsuario(UsuarioPutRequestBody usuarioPutRequestBody) {
		Usuario usuario = repositorio.findById(usuarioPutRequestBody.getId())
				          .orElseThrow(() -> new ResourceNotFoundException(usuarioPutRequestBody.getId()));
		
		UsuarioMapper.INSTANCE.AtualizarUsuario(usuarioPutRequestBody,usuario);
		repositorio.save(usuario);
	}
}
