package com.ey.service;

import org.springframework.stereotype.Service;

import com.ey.dto.CustomerResponse;
import com.ey.entity.User;
import com.ey.repository.UserRepository;
import com.ey.serviceInterface.CustomerServiceInterface;

@Service
public class CustomerService implements CustomerServiceInterface{
	
	private UserRepository userRepository;

	public CustomerService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public CustomerResponse getCustomer(String email) {
		User customer = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		return new CustomerResponse(customer.getId(),customer.getName(),customer.getEmail(),customer.getPhone());
	}
	
	

}
