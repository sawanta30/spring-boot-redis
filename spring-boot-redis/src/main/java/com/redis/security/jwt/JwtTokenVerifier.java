package com.redis.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenVerifier extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authHeader.replace("Bearer ", "");
		String key = "secure";
		Jws<Claims> claims = Jwts.parser().setSigningKey(key.getBytes())
			.parseClaimsJws(token);
		
		Claims body = claims.getBody();
		
		String username = body.getSubject();
		
		List<Map<String,String>> authorities = (List<Map<String, String>>) body.get("authorities");
		Set<SimpleGrantedAuthority> sga = authorities.stream().map(a->new SimpleGrantedAuthority(a.get("authority"))).collect(Collectors.toSet());
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,sga);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
