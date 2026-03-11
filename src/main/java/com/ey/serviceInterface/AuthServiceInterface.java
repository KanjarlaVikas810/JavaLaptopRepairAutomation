package com.ey.serviceInterface;

import com.ey.dto.AuthResponse;
import com.ey.dto.LoginRequest;
import com.ey.dto.RegisterRequest;
import com.ey.dto.UserResponse;

public interface AuthServiceInterface {

	UserResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

}
