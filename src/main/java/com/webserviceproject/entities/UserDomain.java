package com.webserviceproject.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_userdomain")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id","name"})
@ToString(exclude = {"order"})
public class UserDomain implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "the name cannot be empty")
	private String name;
	@NotEmpty(message = "the email cannot be empty")
	private String email;
	@NotEmpty(message = "the phone name cannot be empty")
	private String phone;
	@NotEmpty(message = "the password cannot be empty")
	private String password;
	@NotEmpty(message = "the username cannot be empty")
	@Column(nullable = false,unique = true)
	private String username;
	
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(name = "tb_userdomain_roles",joinColumns = @JoinColumn(name = "userdomain_id"),
													   inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<RoleModel> roles = new ArrayList<>();
	
	@OneToMany(mappedBy = "userDomain",cascade = CascadeType.REMOVE)
	private Set<Order> order = new HashSet<>();

	public UserDomain(Long id, String name, String email, String phone, String password,String username) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.username = username;
	}

	@JsonIgnore
	public Set<Order> getOrder() {
		return order;
	}

}
