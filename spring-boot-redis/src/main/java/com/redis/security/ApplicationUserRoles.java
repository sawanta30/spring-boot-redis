package com.redis.security;

import static com.redis.security.ApplicationUserPermissions.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;
public enum ApplicationUserRoles {

	ADMIN(Sets.newHashSet(EMPLOYEE_READ.getPermission(),EMPLOYEE_WRITE.getPermission(),ADMIN_WRITE.getPermission())),
	ADMINTRAINEE(Sets.newHashSet(EMPLOYEE_READ.getPermission()));
	
	private final Set<String> permission;

	
	public Set<String> getPermission() {
		return permission;
	}


	private ApplicationUserRoles(Set<String> permission) {
		this.permission = permission;
	}
	
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		Set<SimpleGrantedAuthority> permissions = permission.stream().map(p->new SimpleGrantedAuthority(p))
				.collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		return permissions;
	}
	
	
}
