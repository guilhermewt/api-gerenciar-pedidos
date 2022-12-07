package com.webserviceproject.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.Usuario;
import com.webserviceproject.mapper.UsuarioMapper;
import com.webserviceproject.repository.RoleModelRepositorio;
import com.webserviceproject.repository.UsuarioRepositorio;
import com.webserviceproject.request.UsuarioPostRequestBody;
import com.webserviceproject.request.UsuarioPutRequestBody;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.services.exceptions.ConflictException;
import com.webserviceproject.services.exceptions.DataBaseException;
import com.webserviceproject.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UsuarioService implements UserDetailsService{
	
	private final UsuarioRepositorio repositorio;
	
	private final RoleModelRepositorio roleModelRepositorio;
	
	private final GetAuthenticatedUser authenticatedUser;

	public List<Usuario> findAll() {
		return repositorio.findAll();
	}

	public Usuario findById(long id) {
		return repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Usuario getMyUser() {
		return authenticatedUser.userAuthenticated();
	}

	public Usuario insertUsuarioUser(UsuarioPostRequestBody usuarioPostRequestBody) {
		checkIfObjectAlreadyExistsInDatabase(usuarioPostRequestBody.getUsername());
		
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody);
		usuario.getRoles().add(roleModelRepositorio.findById(2l).get());//role user
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		
		return repositorio.save(usuario);
		
	}
	
	public Usuario insertUsuarioAdmin(UsuarioPostRequestBody usuarioPostRequestBody) {
		checkIfObjectAlreadyExistsInDatabase(usuarioPostRequestBody.getUsername());
		
		Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody);
		usuario.getRoles().add(roleModelRepositorio.findById(1l).get());//role admin
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		
		return repositorio.save(usuario);
	}

	public void delete(long id) {
		try {
			repositorio.delete(findById(id));
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	public void update(UsuarioPutRequestBody usuarioPutRequestBody) {
		Usuario usuario = getMyUser();
		
		UsuarioMapper.INSTANCE.AtualizarUsuario(usuarioPutRequestBody,usuario);
		
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		repositorio.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repositorio.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("userDomain not found"));
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
	}
	
	public void checkIfObjectAlreadyExistsInDatabase(String username) {
		if(repositorio.findByUsername(username).isPresent()) {
			throw new ConflictException("this object already exists");
		}
	}
	
}
