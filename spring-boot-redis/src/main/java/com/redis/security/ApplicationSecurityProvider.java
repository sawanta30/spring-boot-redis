package com.redis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityProvider extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails sawanta30 = User.builder()
							.username("sawanta30")
							.password(passwordEncoder.encode("Akshay@30"))
							.roles(ApplicationUserRoles.ADMIN.name())//ROLE_ADMIN
							.build();
		UserDetails sawantas007 = User.builder()
				.username("sawantas007")
				.password(passwordEncoder.encode("Akshay@30"))
				.roles(ApplicationUserRoles.ADMINTRAINEE.name())//ROLE_ADMINTRAINEE
				.build();
		
		return new InMemoryUserDetailsManager(sawanta30,sawantas007);
		
	}


	
	
}
