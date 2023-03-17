package com.webserviceproject.request;

import java.util.function.Function;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.domain.Page;

import com.webserviceproject.entities.UserDomain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDomainGetRequestBody {
	
	private Long id;
	@NotEmpty(message = "the name cannot be empty")
	private String name;
	@NotEmpty(message = "the email cannot be empty")
	private String email;
	@NotEmpty(message = "the phone name cannot be empty")
	private String phone;
	@NotEmpty(message = "the username cannot be empty")
	private String username;

	public UserDomainGetRequestBody(Long id,String name, String email, String phone,String username) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.username = username;
	}
	
	public static Page<UserDomainGetRequestBody> toUserDomainGetRequestBody(Page<UserDomain> userDomain){
		
		Page<UserDomainGetRequestBody> dtoPage = userDomain.map(new Function<UserDomain,UserDomainGetRequestBody>() {
			
			@Override
			public UserDomainGetRequestBody apply(UserDomain userDomain) {
				UserDomainGetRequestBody dto = new UserDomainGetRequestBody();
				dto.setId(userDomain.getId());
				dto.setName(userDomain.getName());
				dto.setEmail(userDomain.getEmail());
				dto.setUsername(userDomain.getUsername());
				dto.setPhone(userDomain.getPhone());
				return dto;
			}
		});
		return dtoPage;		
	}
}
