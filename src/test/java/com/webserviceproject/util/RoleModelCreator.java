package com.webserviceproject.util;

import com.webserviceproject.entities.RoleModel;
import com.webserviceproject.enums.RoleName;

public class RoleModelCreator {
	
	public static RoleModel createRoleModelADMIN() {
		RoleModel roleModel = new RoleModel(1l, RoleName.ROLE_ADMIN);
		return roleModel;
	}
	
	public static RoleModel createRoleModelUSER() {
		RoleModel roleModel = new RoleModel(2l, RoleName.ROLE_USER);
		return roleModel;
	}
	
	public static RoleModel createRoleModelToBeUpdate() {
		RoleModel roleModel = new RoleModel(2l, RoleName.ROLE_USER);
		return roleModel;
	}
}
