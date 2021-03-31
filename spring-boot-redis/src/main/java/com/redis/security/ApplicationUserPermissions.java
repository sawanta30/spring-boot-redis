package com.redis.security;

public enum ApplicationUserPermissions {

	EMPLOYEE_READ("employee:read"),
	EMPLOYEE_WRITE("employee:write"),
	ADMIN_WRITE("admin:write");

	private final String permission;
	
	ApplicationUserPermissions(String permission) {
		this.permission=permission;
	}
	public String getPermission() {
		return permission;
	}
	
}
