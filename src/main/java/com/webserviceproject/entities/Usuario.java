package com.webserviceproject.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of= {"id","nome"})
@ToString(exclude = {"pedido"})
public class Usuario implements Serializable,UserDetails{
	/*
	 * comeca a usar os novos tratamento de excessoes
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "the nome cannot be empty")
	private String nome;
	@NotEmpty(message = "the email cannot be empty")
	private String email;
	@NotEmpty(message = "the telefone nome cannot be empty")
	private String telefone;
	@NotEmpty(message = "the password cannot be empty")
	private String password;
	@NotEmpty(message = "the username cannot be empty")
	@Column(nullable = false,unique = true)
	private String username;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "tb_usuario_roles",joinColumns = @JoinColumn(name = "usuario_id"),
													   inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<RoleModel> roles = new ArrayList<>();
	
	@OneToMany(mappedBy = "usuario",cascade = CascadeType.REMOVE)
	private Set<Pedido> pedido = new HashSet<>();

	public Usuario(Long id, String nome, String email, String telefone, String password,String username) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.password = password;
		this.username = username;
	}

	@JsonIgnore
	public Set<Pedido> getPedido() {
		return pedido;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}	
}
