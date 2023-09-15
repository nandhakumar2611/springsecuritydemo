package com.example.springsecuritynew.service;

import java.util.List;
import java.util.Optional;

import com.example.springsecuritynew.model.Role;

public interface RoleService {
	
	Role createRole(Role role);
	
	Optional<Role> findByRole(String Role);
	
	List<Role> getAllRole();
	
	int deleteRoleById(Long id);
	
	Role updateRoleById(Long id, Role updateRole);

}
