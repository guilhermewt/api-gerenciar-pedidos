package com.webserviceproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
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

import com.webserviceproject.mapper.UserDomainMapper;
import com.webserviceproject.request.UserDomainGetRequestBody;
import com.webserviceproject.request.UserDomainPostRequestBody;
import com.webserviceproject.request.UserDomainPutRequestBody;
import com.webserviceproject.services.UserDomainService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/userdomains")
@RequiredArgsConstructor
public class UserDomainController {

	private final UserDomainService userDomainsService;

	@GetMapping(value = "/admin/all/")
	@Operation(summary = "find all user domain non paginated, the user has to be admin", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<List<UserDomainGetRequestBody>> findAllNonPageable() {
		return ResponseEntity
				.ok(UserDomainMapper.INSTANCE.toUserDomainGetRequestBody(userDomainsService.findAllNonPageable()));
	}

	@GetMapping(value = "/admin/all/Pageable")
	@Operation(summary = "find all userdomain paginated", description = "the default size is 20, use the parameter to change the default value, the user has to be admin", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Page<UserDomainGetRequestBody>> findAllPageable(@ParameterObject Pageable pageable) {
		return ResponseEntity
				.ok(UserDomainGetRequestBody.toUserDomainGetRequestBody(userDomainsService.findAllPageable(pageable)));
	}

	@GetMapping(value = "/admin/{id}")
	@Operation(summary = "find userdomain by id", description = "the user has to be admin", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<UserDomainGetRequestBody> findById(@PathVariable long id) {
		return ResponseEntity.ok(UserDomainMapper.INSTANCE
				.toUserDomainGetRequestBody(userDomainsService.findByIdOrElseThrowBadRequestException(id)));
	}

	@GetMapping(value = "/get-user-authenticated")
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<UserDomainGetRequestBody> getMyUser() {
		return ResponseEntity.ok(UserDomainMapper.INSTANCE.toUserDomainGetRequestBody(userDomainsService.getMyUser()));
	}

	@PostMapping(value = "/admin")
	@Operation(summary = "create user admin", description = "the user has to be admin", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<UserDomainGetRequestBody> insertUserAdmin(
			@RequestBody @Valid UserDomainPostRequestBody userDomainPostRequestBody) {
		return new ResponseEntity<UserDomainGetRequestBody>(UserDomainMapper.INSTANCE.toUserDomainGetRequestBody(
				userDomainsService.insertUserDomainAdmin(userDomainPostRequestBody)), HttpStatus.CREATED);
	}

	@PostMapping(value = "/saveuserdomain")
	@Operation(summary = "create user", description = "for a common user to register", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<UserDomainGetRequestBody> insertUser(
			@RequestBody @Valid UserDomainPostRequestBody userDomainPostRequestBody) {
		return new ResponseEntity<UserDomainGetRequestBody>(
				UserDomainMapper.INSTANCE.toUserDomainGetRequestBody(
						userDomainsService.insertUserDomainWithRoleUser(userDomainPostRequestBody)),
				HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/admin/{id}")
	@Operation(description = "the user has to be admin", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation"),
			@ApiResponse(responseCode = "400", description = "when userdomain does not exist in the dataBase") })
	public ResponseEntity<Void> delete(@PathVariable long id) {
		userDomainsService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	@Operation(summary = "update userdomain authenticated", description = "the user has to be admin", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Void> update(@RequestBody UserDomainPutRequestBody userDomainPutRequestBody) {
		userDomainsService.update(userDomainPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/admin/update-full/{id}")
	@Operation(summary = "update any user domain", description = "to update any user domain the user has to be admin", security = {
			@SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<Void> updateFull(@RequestBody UserDomainPutRequestBody userDomainPutRequestBody,
			@PathVariable long id) {
		userDomainsService.updateAnyUserDomain(userDomainPutRequestBody, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
