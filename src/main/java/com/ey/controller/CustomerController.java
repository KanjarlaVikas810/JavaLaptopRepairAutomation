package com.ey.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.CustomerResponse;
import com.ey.dto.ServiceRequestResponse;
import com.ey.serviceInterface.CustomerServiceInterface;
import com.ey.serviceInterface.ServiceRequestServiceInterface;

@RestController
@RequestMapping("/laptop-service/customers")
@CrossOrigin(origins="http://localhost:3000")
public class CustomerController {
	
	private CustomerServiceInterface service;
	private ServiceRequestServiceInterface serviceRequest;

	public CustomerController(CustomerServiceInterface service, ServiceRequestServiceInterface serviceRequest) {
		super();
		this.service = service;
		this.serviceRequest = serviceRequest;
	}

	@GetMapping
	public ResponseEntity<CustomerResponse> getCustomer(Authentication auth) {
		return ResponseEntity.ok(service.getCustomer(auth.getName()));
	}
	@GetMapping("/requests")
	public ResponseEntity<List<ServiceRequestResponse>> getServices(Authentication auth){
		
		return ResponseEntity.ok(serviceRequest.getCustomerServices(auth.getName()));
		
	}
	
	@PutMapping("/{requestId}/approval")
	public ResponseEntity<ServiceRequestResponse> approval(@PathVariable Long requestId,@RequestParam boolean approved,Authentication auth){
		
		return ResponseEntity.ok(serviceRequest.approval(requestId,approved,auth.getName()));
		
	}
	

}
