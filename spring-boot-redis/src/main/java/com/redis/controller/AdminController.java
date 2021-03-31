package com.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.model.User;
import com.redis.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	UserService userService;
	
	
	@PostMapping("/users")
	@PreAuthorize("hasAuthority('admin:write')")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@GetMapping("/users/{username}")
	@PreAuthorize("hasAuthority('admin:write')")
	public User getUserByUsername(@PathVariable("username") String username) {
		return userService.getUserByUsername(username);
	}
	
	@GetMapping("/users")
	@PreAuthorize("hasAuthority('admin:write')")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasAuthority('admin:write')")
	public void deleteEmployeeById(@PathVariable("id") String id) {
		userService.deleteUserById(id);
	}
}
