package com.webserviceproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webserviceproject.entities.UserDomain;
import com.webserviceproject.request.UserDomainPostRequestBody;
import com.webserviceproject.request.UserDomainPutRequestBody;
import com.webserviceproject.services.UserDomainService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/userdomains")
@RequiredArgsConstructor
public class UserDomainController {
    
	private final UserDomainService userDomainsService;
	
	@GetMapping(value = "/admin/all/")
	public ResponseEntity<List<UserDomain>> findAllNonPageable(){
		return ResponseEntity.ok(userDomainsService.findAllNonPageable());
	}
	
	@GetMapping(value = "/admin/all/Pageable")
	public ResponseEntity<Page<UserDomain>> findAllPageable(Pageable pageable){
		return ResponseEntity.ok(userDomainsService.findAllPageable(pageable));
	}
	
	@GetMapping(value="/admin/{id}")
	public ResponseEntity<UserDomain> findById(@PathVariable long id){
		return ResponseEntity.ok(userDomainsService.findById(id));
	}
	
	@PostMapping(value = "/admin")
	public ResponseEntity<UserDomain> insertUserAdmin(@RequestBody @Valid UserDomainPostRequestBody userDomainPostRequestBody){
		return new ResponseEntity<UserDomain>(userDomainsService.insertUserDomainAdmin(userDomainPostRequestBody), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/saveuserdomain")
	public ResponseEntity<UserDomain> insertUser(@RequestBody @Valid UserDomainPostRequestBody userDomainPostRequestBody){
		return new ResponseEntity<UserDomain>(userDomainsService.insertUserDomainWithRoleUser(userDomainPostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/admin/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		userDomainsService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> update(@RequestBody UserDomainPutRequestBody userDomainPutRequestBody){
		userDomainsService.update(userDomainPutRequestBody);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
