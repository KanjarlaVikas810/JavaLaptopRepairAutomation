package com.ey.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ey.dto.AuthResponse;
import com.ey.dto.LoginRequest;
import com.ey.dto.RegisterRequest;
import com.ey.dto.UserResponse;
import com.ey.entity.User;
import com.ey.enums.Role;
import com.ey.repository.UserRepository;
import com.ey.security.JwtUtil;
import com.ey.serviceInterface.AuthServiceInterface;


@Service
public class AuthService implements AuthServiceInterface{
	
	private UserRepository userRepository;
	private PasswordEncoder encoder;
	private JwtUtil jwtUtil;
	
	

	public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil) {
		super();
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public UserResponse register(RegisterRequest request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		
		User user = new User(
				request.getName(),
				request.getEmail(),
				request.getPhone(),
				encoder.encode(request.getPassword()),
				Role.CUSTOMER);
		
		User savedUser = userRepository.save(user);
		
		return new UserResponse(savedUser.getId(),savedUser.getName(),savedUser.getEmail(),savedUser.getPhone(),savedUser.getRole());
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		User user=userRepository.findByEmail(request.getEmail())
				.orElseThrow(()->new RuntimeException("User not found"));
		
		if(!encoder.matches(request.getPassword(), user.getPassword())){
			throw new RuntimeException("Invlid Password");
		}
		
		String token=jwtUtil.generateToken(user.getEmail(), user.getRole().name());
		
		return new AuthResponse(
				token,
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getRole().name());
	}

}
