package com.ey.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private JwtUtil jwtUtil;
	private CustomUserDetailsService userDetailsSevice;
	public JwtAuthenticationFilter(JwtUtil jwtutil, CustomUserDetailsService userDetailsSevice) {
		super();
		this.jwtUtil = jwtutil;
		this.userDetailsSevice = userDetailsSevice;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Jwt Filter ");
		final String header= request.getHeader("Authorization");
		String token=null;
		String username=null;
		String role=null;
		
		if(header!= null && header.startsWith("Bearer ")) {
			token=header.substring(7);
			System.out.println("extracted from 1st condition :"+role);
			if(jwtUtil.validateToken(token)) {
				username=jwtUtil.extractUsername(token);
				role=jwtUtil.extractRole(token);
				System.out.println("extracted from 2nd :"+role+" username:"+username);
			}
		}
		
		if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
//			
			System.out.println("extracted from token:"+role);
			UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(username,null,
					List.of(new SimpleGrantedAuthority("ROLE_"+role)));
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		
		filterChain.doFilter(request, response);
		
	}
	

}
