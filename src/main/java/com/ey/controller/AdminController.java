package com.ey.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.BranchDto;
import com.ey.dto.BranchResponse;
import com.ey.dto.CustomerResponse;
import com.ey.dto.InvoiceResponse;
import com.ey.dto.TechnicianRegister;
import com.ey.dto.TechnicianResponse;
import com.ey.entity.Invoice;
import com.ey.serviceInterface.AdminServiceInterface;
import com.ey.serviceInterface.ServiceRequestServiceInterface;

@RestController
@RequestMapping("/laptop-service/admin")
@CrossOrigin(origins="http://localhost:3000")
public class AdminController {
	
	private AdminServiceInterface service;
	private ServiceRequestServiceInterface serviceRequest;
	
	
	

	public AdminController(AdminServiceInterface service, ServiceRequestServiceInterface serviceRequest) {
		super();
		this.service = service;
		this.serviceRequest = serviceRequest;
	}

	@PostMapping("/branch")
	public ResponseEntity<BranchResponse> register(@RequestBody BranchDto request) {
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.registerBranch(request));
	}
	
	@GetMapping("/branch")
	public ResponseEntity<List<BranchResponse>> getAllBranches() {
		return ResponseEntity.ok(service.getAllBranches());
	}
	
	@DeleteMapping("/branch/{id}")
	public ResponseEntity<String> deleteBranch(@PathVariable Long id) {
		service.deleteBranch(id);
		return ResponseEntity.ok("Deleted Branch with id= "+id+" Successfully");
	}
	
	@PostMapping("/technician")
	public ResponseEntity<TechnicianResponse> register(@RequestBody TechnicianRegister request) {
		return  ResponseEntity.status(HttpStatus.CREATED).body(service.registerTechnician(request));
	}
	
	@GetMapping("/technician")
	public ResponseEntity<List<TechnicianResponse>> getAllTechnician() {
		return ResponseEntity.ok(service.getAllTechnician());
	}
	
	@GetMapping("/technician/{id}")
	public ResponseEntity<TechnicianResponse> getTechnician(@PathVariable Long id) {
		return ResponseEntity.ok(service.getTechnician(id));
	}
	
	@DeleteMapping("/technician/{id}")
	public ResponseEntity<String> deleteTechnician(@PathVariable Long id) {
		return ResponseEntity.ok(service.deleteTechnician(id));
	}
	
	
	@GetMapping("/customer")
	public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
		return ResponseEntity.ok(service.getAllCustomers());
	}
	
	@GetMapping("/invoices")
	public ResponseEntity<List<InvoiceResponse>> getInvoices(){
		return ResponseEntity.ok(serviceRequest.getInvoices());
	}
	
	
	@GetMapping("/invoices/branch")
	public ResponseEntity<List<InvoiceResponse>> getInvoicesByBranch(@RequestParam Long id){
		return ResponseEntity.ok(service.getInvoicesByBranch(id));
	}
	
	@GetMapping("/invoices/branches")
	public ResponseEntity<List<InvoiceResponse>> getInvoicesByBranchAndPay(@RequestParam Long id,@RequestParam boolean paid){
		return ResponseEntity.ok(service.getInvoicesByBranchAndPay(id,paid));
	}
	

}
