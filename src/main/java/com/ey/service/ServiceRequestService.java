package com.ey.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ey.azure.AzureBlobService;
import com.ey.dto.DiagnosisUpdateDto;
import com.ey.dto.InvoiceResponse;
import com.ey.dto.ServiceRequestCreateDto;
import com.ey.dto.ServiceRequestResponse;
import com.ey.entity.Invoice;
import com.ey.entity.ServiceRequest;
import com.ey.entity.User;
import com.ey.enums.ServiceStatus;
import com.ey.repository.InvoiceRepository;
import com.ey.repository.ServiceRequestRepository;
import com.ey.repository.UserRepository;
import com.ey.serviceInterface.ServiceRequestServiceInterface;

@Service
public class ServiceRequestService implements ServiceRequestServiceInterface{
	
	private ServiceRequestRepository serviceRequestRepository;
	private UserRepository userRepository;
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private  AzureBlobService azureBlobService;
	
	public ServiceRequestService(ServiceRequestRepository serviceRequest, UserRepository userRepository,
			InvoiceRepository invoiceRepository) {
		super();
		this.serviceRequestRepository = serviceRequest;
		this.userRepository = userRepository;
		this.invoiceRepository = invoiceRepository;
	}
	
	
	@Override
	public ServiceRequestResponse createRequest(ServiceRequestCreateDto request, MultipartFile image,String email) {
		User customer=userRepository.findById(request.getCustomerId())
				.orElseThrow(()-> new RuntimeException("customer not found"));
		
		User technician=userRepository.findByEmail(email)
				
				.orElseThrow(()-> new RuntimeException("Technician not found"));
		ServiceRequest service=new ServiceRequest(request.getLaptopModel(),request.getIssueDescription(),ServiceStatus.NEW,customer,technician,LocalDateTime.now());
		service.setBranch(technician.getBranch());
		
		String imageUrl=null;
		if(image != null && !image.isEmpty()) {
			imageUrl = azureBlobService.uploadFile(image);
		}
		service.setImageUrl(imageUrl);
		
		ServiceRequest saved=serviceRequestRepository.save(service);
		
		
		
		return new ServiceRequestResponse(
				saved.getId(),
				saved.getLaptopModel(),
				saved.getIssueDescription(),
				saved.getDiagnosis(),
				saved.getStatus(),
				saved.getCost(),
				saved.getCreatedAt(),
				saved.getDeliveryDate());
	}


	@Override
	public ServiceRequestResponse updateDiagnosis(Long id, DiagnosisUpdateDto request) {
		ServiceRequest service=serviceRequestRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Service does not exixts"));
		service.setCost(request.getCost());
		service.setDiagnosis(request.getDiagnosis());
		service.setDeliveryDate(request.getDeliveryDate());
		service.setStatus(ServiceStatus.WAITING_FOR_CUSTOMER_APPROVAL);
		ServiceRequest saved=serviceRequestRepository.save(service);
		
		return new ServiceRequestResponse(
				saved.getId(),
				saved.getLaptopModel(),
				saved.getIssueDescription(),
				saved.getDiagnosis(),
				saved.getStatus(),
				saved.getCost(),
				saved.getCreatedAt(),
				saved.getDeliveryDate());
	}


	@Override
	public ServiceRequestResponse updatestatus(Long id, ServiceStatus status) {
		ServiceRequest service=serviceRequestRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Service does not exixts"));
		if(service.getStatus() == ServiceStatus.REJECTED || service.getStatus() == ServiceStatus.CLOSED) {
			throw new RuntimeException("cannot update status for rejected or closed service");
		}
		
		service.setStatus(status);
		ServiceRequest saved=serviceRequestRepository.save(service);
		
		return new ServiceRequestResponse(
				saved.getId(),
				saved.getLaptopModel(),
				saved.getIssueDescription(),
				saved.getDiagnosis(),
				saved.getStatus(),
				saved.getCost(),
				saved.getCreatedAt(),
				saved.getDeliveryDate());
	}


	@Override
	public InvoiceResponse generateInvoice(Long id, String name) {
		ServiceRequest service=serviceRequestRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Service does not exixts"));
		
		if(service.getStatus()!= ServiceStatus.DONE) {
			throw new RuntimeException("Service is not marked DONE");
		}
		Invoice invoice= new Invoice(service,service.getCost());
		Invoice saved=invoiceRepository.save(invoice);
		return new InvoiceResponse(saved.getId(),saved.getAmount(),saved.isPaid(),saved.getGeneratedAt(),saved.getServiceRequest().getBranch().getId());
	}


	@Override
	public List<ServiceRequestResponse> getServices(String email) {
		User technician =userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("Technician not found"));
		
		return serviceRequestRepository.findByTechnician(technician)
				.stream()
				.map(saved ->  new ServiceRequestResponse(
				saved.getId(),
				saved.getCustomer().getName(),
				saved.getCustomer().getId(),
				saved.getLaptopModel(),
				saved.getIssueDescription(),
				saved.getDiagnosis(),
				saved.getStatus(),
				saved.getCost(),
				saved.getCreatedAt(),
				invoiceRepository.findByServiceRequest(saved)
				.map(Invoice::isPaid).orElse(false),
				invoiceRepository.existsByServiceRequest(saved),
				invoiceRepository.findByServiceRequest(saved)
				.map(Invoice::getId).orElse(-1L),
				saved.getImageUrl(),
				saved.getDeliveryDate()))
				.collect(Collectors.toList());
				
		
	}
	@Override
	public List<ServiceRequestResponse> getCustomerServices(String email) {
		User customer =userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("customer not found"));
		return serviceRequestRepository.findByCustomer(customer)
				.stream()
				.map(saved ->  new ServiceRequestResponse(
				saved.getId(),
				saved.getCustomer().getName(),
				saved.getCustomer().getId(),
				saved.getLaptopModel(),
				saved.getIssueDescription(),
				saved.getDiagnosis(),
				saved.getStatus(),
				saved.getCost(),
				saved.getCreatedAt(),
				invoiceRepository.findByServiceRequest(saved)
				.map(Invoice::isPaid).orElse(false),
				invoiceRepository.existsByServiceRequest(saved),
				invoiceRepository.findByServiceRequest(saved)
				.map(Invoice::getId).orElse(-1L),
				saved.getImageUrl(),
				saved.getDeliveryDate()))
				.collect(Collectors.toList());
				
		
	}


	@Override
	public List<ServiceRequestResponse> getCustomerService(Long id, String name) {
		User customer=userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("customer not found"));
				
		return serviceRequestRepository.findByCustomer(customer)
				.stream()
				.map(saved ->  new ServiceRequestResponse(
				saved.getId(),
				saved.getCustomer().getName(),
				saved.getCustomer().getId(),
				saved.getLaptopModel(),
				saved.getIssueDescription(),
				saved.getDiagnosis(),
				saved.getStatus(),
				saved.getCost(),
				saved.getCreatedAt(),
				invoiceRepository.findByServiceRequest(saved)
				.map(Invoice::isPaid).orElse(false),
				invoiceRepository.existsByServiceRequest(saved),
				invoiceRepository.findByServiceRequest(saved)
				.map(Invoice::getId).orElse(-1L),
				saved.getImageUrl(),
				saved.getDeliveryDate()))
				.collect(Collectors.toList());
				
			
	}


	@Override
	public InvoiceResponse invoicePay(Long id) {
		
		

		Invoice invoice=invoiceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Invoice not found"));
		
		ServiceRequest service=serviceRequestRepository.findById(invoice.getServiceRequest().getId())
				.orElseThrow(()-> new RuntimeException("Service does not exixts"));
		if(invoice.isPaid()==true) {
			throw new RuntimeException("Invoice already paid");
		}
		
		invoice.setPaid(true);
		Invoice saved=invoiceRepository.save(invoice);
		return new InvoiceResponse(saved.getId(),saved.getAmount(),saved.isPaid(),saved.getGeneratedAt(),saved.getServiceRequest().getBranch().getId());
	}


	@Override
	public ServiceRequestResponse approval(Long requestId, boolean approved, String email) {
		ServiceRequest service = serviceRequestRepository.findById(requestId)
				.orElseThrow(() -> new RuntimeException("service not found"));
		if(!email.equals(service.getCustomer().getEmail())) {
			throw new RuntimeException("Unauthoried Access");
		}
		
		if(service.getStatus() != ServiceStatus.WAITING_FOR_CUSTOMER_APPROVAL)
		{
			throw new RuntimeException("Request not waiting for customer");
		}
		if(approved) {
			service.setStatus(ServiceStatus.IN_PROGRESS);
		}else {
			service.setStatus(ServiceStatus.REJECTED);
		}
		ServiceRequest saved=serviceRequestRepository.save(service);
		
		return new ServiceRequestResponse(
				saved.getId(),
				saved.getLaptopModel(),
				saved.getIssueDescription(),
				saved.getDiagnosis(),
				saved.getStatus(),
				saved.getCost(),
				saved.getCreatedAt(),
				saved.getDeliveryDate());
	}


	@Override
	public List<InvoiceResponse> getInvoices() {
		return invoiceRepository.findAll().stream()
				.map(saved -> new InvoiceResponse(saved.getId(),
						saved.getAmount(),
						saved.isPaid(),
						saved.getGeneratedAt(),
						saved.getServiceRequest().getBranch().getId())).collect(Collectors.toList());
	}




	
	
	
	
	
	
	
	
	
	
	
	
}
