package com.ey.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.AuthResponse;
import com.ey.dto.LoginRequest;
import com.ey.dto.RegisterRequest;
import com.ey.dto.UserResponse;
import com.ey.serviceInterface.AuthServiceInterface;

@RestController
@RequestMapping("/laptop-service/auth")
@CrossOrigin(origins= "http://localhost:3000")
public class AuthController {
	
	private AuthServiceInterface service;

	public AuthController(AuthServiceInterface service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
		return  ResponseEntity.ok(service.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		System.out.println("triggered by react"+request.getEmail());
		return ResponseEntity.ok(service.login(request));
	}
	
}
