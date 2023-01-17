package com.webserviceproject.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.mapper.UserDomainMapper;
import com.webserviceproject.repository.RoleModelRepository;
import com.webserviceproject.repository.UserDomainRepository;
import com.webserviceproject.request.UserDomainPostRequestBody;
import com.webserviceproject.request.UserDomainPutRequestBody;
import com.webserviceproject.services.authentication.GetAuthenticatedUser;
import com.webserviceproject.services.exceptions.BadRequestException;
import com.webserviceproject.services.exceptions.ConflictException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserDomainService implements UserDetailsService{
	
	private final UserDomainRepository userDomainRepository;
	
	private final RoleModelRepository roleModelRepository;
	
	private final GetAuthenticatedUser authenticatedUser;

	public List<UserDomain> findAllNonPageable() {
		return userDomainRepository.findAll();
	}
	
	public Page<UserDomain> findAllPageable(Pageable pageable) {
		return userDomainRepository.findAll(pageable);
	}

	public UserDomain findByIdOrElseThrowBadRequestException(long id) {
		return userDomainRepository.findById(id).orElseThrow(() -> new BadRequestException("userDomain not found"));
	}
	
	public UserDomain getMyUser() {
		return authenticatedUser.userAuthenticated();
	}

	public UserDomain insertUserDomainWithRoleUser(UserDomainPostRequestBody userDomainPostRequestBody) {
		checkIfObjectAlreadyExistsInDatabase(userDomainPostRequestBody.getUsername());
		
		UserDomain userDomain = UserDomainMapper.INSTANCE.toUserDomain(userDomainPostRequestBody);
		userDomain.getRoles().add(roleModelRepository.findById(2l).get());//role user
		userDomain.setPassword(new BCryptPasswordEncoder().encode(userDomain.getPassword()));
		
		return userDomainRepository.save(userDomain);
		
	}
	
	public UserDomain insertUserDomainAdmin(UserDomainPostRequestBody userDomainPostRequestBody) {
		checkIfObjectAlreadyExistsInDatabase(userDomainPostRequestBody.getUsername());
		
		UserDomain userDomain = UserDomainMapper.INSTANCE.toUserDomain(userDomainPostRequestBody);
		userDomain.getRoles().add(roleModelRepository.findById(1l).get());//role admin
		userDomain.setPassword(new BCryptPasswordEncoder().encode(userDomain.getPassword()));
		
		return userDomainRepository.save(userDomain);
	}

	public void delete(long id) {
		try {
			userDomainRepository.delete(findByIdOrElseThrowBadRequestException(id));
		} 
		catch (DataIntegrityViolationException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public void update(UserDomainPutRequestBody userDomainPutRequestBody) {
		UserDomain savedUserDomain = userDomainRepository.findById(authenticatedUser.userAuthenticated().getId()).get();
		
		UserDomain userDomain = UserDomainMapper.INSTANCE.updateUserDomain(userDomainPutRequestBody,savedUserDomain);
		
		userDomain.setPassword(new BCryptPasswordEncoder().encode(userDomain.getPassword()));
		userDomainRepository.save(userDomain);
	}
	
	public void updateAnyUserDomain(UserDomainPutRequestBody userDomainPutRequestBody,long id) {
		UserDomain savedUserDomain = userDomainRepository.findById(id).get();
		
		UserDomain userDomain = UserDomainMapper.INSTANCE.updateUserDomain(userDomainPutRequestBody,savedUserDomain);
		
		userDomain.setPassword(new BCryptPasswordEncoder().encode(userDomain.getPassword()));
		userDomainRepository.save(userDomain);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDomain userDomain = userDomainRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("userDomain not found"));
		return new User(userDomain.getUsername(), userDomain.getPassword(), true, true, true, true, userDomain.getAuthorities());
	}
	
	public void checkIfObjectAlreadyExistsInDatabase(String username) {
		if(userDomainRepository.findByUsername(username).isPresent()) {
			throw new ConflictException("this username already exists");
		}
	}
	
}
