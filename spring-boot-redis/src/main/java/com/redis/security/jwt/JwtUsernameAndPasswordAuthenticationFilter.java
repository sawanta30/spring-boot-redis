package com.redis.security.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	
	AuthenticationManager authenticationManager;

	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UsernameAndPasswordAuthenticationRequest usernameAndPasswordAuthenticationRequest = null;
		try {
			usernameAndPasswordAuthenticationRequest = new ObjectMapper()
					.readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
		} catch (IOException e) {

			e.printStackTrace();
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				usernameAndPasswordAuthenticationRequest.getUsername(),
				usernameAndPasswordAuthenticationRequest.getPassword());

		return authenticationManager.authenticate(authentication);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String token = Jwts.builder().setSubject(authResult.getName())
			.claim("authorities", authResult.getAuthorities())
			.setIssuedAt(new Date())
			.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
			.signWith(SignatureAlgorithm.HS512,"secure".getBytes())
			.compact();
		response.addHeader("Authorization", "Bearer "+token);
			
	}
	
	

}
