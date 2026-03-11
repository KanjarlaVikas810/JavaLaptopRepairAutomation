package com.ey.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final String jwtSecret="thisIsASeceretKeyThatIsAtleast32CharactersLong";
	
	private final long jwtExpirationMs=86400000;
	private SecretKey getSigningKey() {

		byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);

		return Keys.hmacShaKeyFor(keyBytes);

	}
	
	public String generateToken(String username, String role) {
		System.out.println("generate token");

		return Jwts.builder()
				.setSubject(username)
				.claim("role", role)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();

	}
	
	public String extractRole(String token) {
		
		System.out.println("extarct role");
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.get("role",String.class);

	}
	
	public String extractUsername(String token) {
		System.out.println("extract username");

		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();

	}
	
	public Date extractExpiration(String token) {
		

		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getExpiration();

	}

	
	public boolean validateToken(String token) {
		System.out.println("validate Token");
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			System.out.println("Token Valid");
			return true;
		}catch(JwtException e) {
			System.out.println("Token Invalid "+e.getMessage());
			return false;
		}
	}

}
