package com.webserviceproject.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webserviceproject.enums.RoleName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_role")
@NoArgsConstructor
@Data
@ToString(exclude = "usuario")
public class RoleModel implements GrantedAuthority, Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private RoleName roleName;
	
	@ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
	private List<Usuario> usuario;

	public RoleModel(long roleId, RoleName roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	@Override
	public String getAuthority() {
		return this.roleName.toString();
	}
	
	@JsonIgnore
	public List<Usuario> getUsuario() {
		return usuario;
	}

}
