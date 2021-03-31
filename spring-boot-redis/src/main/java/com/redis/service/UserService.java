package com.redis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.redis.dao.UserRepository;
import com.redis.model.User;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public User addUser(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
//		System.out.println(user.getPassword());
//		System.out.println(passwordEncoder);
//		System.out.println(encodedPassword);
		
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}
	
	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public User getUserByUsername(String username) {
		List<User> user = (List<User>) userRepository.findAll();
		return user.parallelStream().filter(u->u.getUsername().equals(username)).findFirst().orElseThrow(()->new UsernameNotFoundException(username));
	}
	
	public void deleteUserById(String id) {
		userRepository.deleteById(id);
	}
	
	public User updateUser(User user,String id) {
		user.setId(id);
		return userRepository.save(user);
	}
}
