package com.ey.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ey.azure.AzureBlobService;
import com.ey.dto.DiagnosisUpdateDto;
import com.ey.dto.InvoiceResponse;
import com.ey.dto.ServiceRequestCreateDto;
import com.ey.dto.ServiceRequestResponse;
import com.ey.enums.ServiceStatus;
import com.ey.serviceInterface.ServiceRequestServiceInterface;

@RestController
@RequestMapping("/laptop-service/technician/requests")
@CrossOrigin(origins="http://localhost:3000")
public class ServiceRequestController {
	
	private ServiceRequestServiceInterface service;


	
	public ServiceRequestController(ServiceRequestServiceInterface service) {
		super();
		this.service = service;
	
	}
	
	
	

	@PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ServiceRequestResponse> createRequest(@RequestPart("data") ServiceRequestCreateDto request,@RequestPart(value="image",required=false) MultipartFile image,Authentication auth) {
		
		
		ServiceRequestResponse response=service.createRequest(request,image,auth.getName());
		return ResponseEntity.ok(response);
		
	}
	
	
	
	@PutMapping("/{id}/diagnosis")
	public ResponseEntity<ServiceRequestResponse> updateDiagnosis(@PathVariable Long id,@RequestBody DiagnosisUpdateDto request) {
		return ResponseEntity.ok(service.updateDiagnosis(id,request));
		
	}
	
	
	
	@PutMapping("/{id}/status")
	public ResponseEntity<ServiceRequestResponse> updateStatus(@PathVariable Long id,@RequestParam ServiceStatus status) {
		return ResponseEntity.ok(service.updatestatus(id,status));
		
	}
	
	@PostMapping("/{id}/invoice")
	public ResponseEntity<InvoiceResponse> generateInvoice(@PathVariable Long id,Authentication auth) {
		System.out.println("***********");
		return ResponseEntity.ok(service.generateInvoice(id,auth.getName()));
		
	}
	
	@PutMapping("/{invoiceId}/invoice/pay")
	public ResponseEntity<InvoiceResponse> invoicePay(@PathVariable Long invoiceId,Authentication auth) {
		return ResponseEntity.ok(service.invoicePay(invoiceId));
		
	}
	
	@GetMapping
	public ResponseEntity<List<ServiceRequestResponse>> getTecnicianServices(Authentication auth) {
		return ResponseEntity.ok(service.getServices(auth.getName()));
		
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<ServiceRequestResponse>> getCustomerService(@PathVariable Long customerId,Authentication auth) {
		return ResponseEntity.ok(service.getCustomerService(customerId,auth.getName()));
		
	}
	
	
	
	
	

}
