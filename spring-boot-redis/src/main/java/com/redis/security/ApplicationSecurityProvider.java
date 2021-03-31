package com.redis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.redis.security.jwt.JwtTokenVerifier;
import com.redis.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityProvider extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
		.addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
		
		.authorizeRequests()
		.antMatchers("/","/css/**","/js/**","index").permitAll()
//		.antMatchers(HttpMethod.POST,"/api/v1/**").hasAuthority(EMPLOYEE_WRITE.getPermission())
//		.antMatchers(HttpMethod.PUT,"/api/v1/**").hasAuthority(EMPLOYEE_WRITE.getPermission())
//		//.antMatchers(HttpMethod.GET,"/api/v1/*").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
//		.antMatchers(HttpMethod.GET,"/api/v1/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
//		.antMatchers(HttpMethod.GET,"/").hasAnyAuthority(null)
		.anyRequest()
		.authenticated();
//		.and()
//		.httpBasic();
		
	}

//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		
//		UserDetails sawanta30 = User.builder()
//							.username("sawanta30")
//							.password(passwordEncoder.encode("Akshay@30"))
////							.roles(ApplicationUserRoles.ADMIN.name())//ROLE_ADMIN
//							.authorities(ADMIN.getGrantedAuthorities())
//							.build();
//		UserDetails sawantas007 = User.builder()
//				.username("sawantas007")
//				.password(passwordEncoder.encode("Akshay@30"))
////				.roles(ApplicationUserRoles.ADMINTRAINEE.name())//ROLE_ADMINTRAINEE
//				.authorities(ADMINTRAINEE.getGrantedAuthorities())
//				.build();
//		
//		return new InMemoryUserDetailsManager(sawanta30,sawantas007);
//		
//	}
	

	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	
	
	
}
