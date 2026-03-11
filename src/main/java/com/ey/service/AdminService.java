package com.ey.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ey.dto.BranchDto;
import com.ey.dto.BranchResponse;
import com.ey.dto.CustomerResponse;
import com.ey.dto.InvoiceResponse;
import com.ey.dto.TechnicianRegister;
import com.ey.dto.TechnicianResponse;
import com.ey.entity.Branch;
import com.ey.entity.Invoice;
import com.ey.entity.User;
import com.ey.enums.Role;
import com.ey.repository.BranchRepository;
import com.ey.repository.InvoiceRepository;
import com.ey.repository.ServiceRequestRepository;
import com.ey.repository.UserRepository;
import com.ey.serviceInterface.AdminServiceInterface;

@Service
public class AdminService implements AdminServiceInterface{
	
	private BranchRepository branchRepository;
	private UserRepository userRepository;
	private ServiceRequestRepository serviceRequest;
	private InvoiceRepository invoiceRepository;
	private PasswordEncoder encoder;

	

	public AdminService(BranchRepository branchRepository, UserRepository userRepository,
			ServiceRequestRepository serviceRequest, InvoiceRepository invoiceRepository, PasswordEncoder encoder) {
		super();
		this.branchRepository = branchRepository;
		this.userRepository = userRepository;
		this.serviceRequest = serviceRequest;
		this.invoiceRepository = invoiceRepository;
		this.encoder = encoder;
	}



	@Override
	public BranchResponse registerBranch(BranchDto request) {
		if(branchRepository.existsByContact(request.getContact()))
		{
			throw new RuntimeException("Branch already exists");
		}
		Branch branch=new Branch();
		branch.setContact(request.getContact());
		branch.setLocation(request.getLocation());
		branch.setName(request.getName());
		
		Branch saved= branchRepository.save(branch);
		return new BranchResponse(saved.getId(),saved.getName(),saved.getLocation(),saved.getContact());
	}



	@Override
	public List<BranchResponse> getAllBranches() {
		return branchRepository.findAll()
				.stream()
				.map(branch-> new BranchResponse(branch.getId(),branch.getName(),branch.getLocation(),branch.getContact()))
				.collect(Collectors.toList());
	}


	@Override
	public void deleteBranch(Long id) {
		branchRepository.deleteById(id);
		
	}

	@Override
	public TechnicianResponse registerTechnician(TechnicianRegister request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		
		 Branch branch=branchRepository.findById(request.getBranchId())
				 .orElseThrow(() -> new RuntimeException("branch not exixts"));
		 
		 User technician = new User(
					request.getName(),
					request.getEmail(),
					request.getPhone(),
					encoder.encode(request.getPassword()),
					Role.TECHNICIAN);
		 technician.setBranch(branch);
		
		User saved = userRepository.save(technician);
		
		return new TechnicianResponse(saved.getId(),saved.getEmail(),saved.getName(),saved.getPhone(),saved.getBranch().getId());
		
		
	}



	@Override
	public List<TechnicianResponse> getAllTechnician() {
		return userRepository.findByRole(Role.TECHNICIAN)
				.stream()
				.map(user -> new TechnicianResponse(user.getId(),user.getEmail(),user.getName(),user.getPhone(),user.getBranch().getId()))
				.collect(Collectors.toList());
	}



	@Override
	public TechnicianResponse getTechnician(Long id) {
		User technician = userRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Technician not found"));
		
		return new TechnicianResponse(technician.getId(),technician.getEmail(),technician.getName(),technician.getPhone(),technician.getBranch().getId());
	}



	@Override
	public String deleteTechnician(Long id) {
		userRepository.deleteById(id);
		return "Technician deleted with id: "+id+" deleted successfully";
	}



	@Override
	public List<CustomerResponse> getAllCustomers() {
		return userRepository.findByRole(Role.CUSTOMER)
				.stream()
				.map(user -> new CustomerResponse(user.getId(),user.getEmail(),user.getName(),user.getPhone()))
				.collect(Collectors.toList());
	}



	@Override
	public List<InvoiceResponse> getInvoicesByBranch(Long id) {
		Branch branch =branchRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Branch does not exists"));
		
		return invoiceRepository.findByServiceRequest_Branch_Id(id).stream()
				.map(saved -> new InvoiceResponse(saved.getId(),
						saved.getAmount(),
						saved.isPaid(),
						saved.getGeneratedAt(),
						saved.getServiceRequest().getBranch().getId())).collect(Collectors.toList());
		
	}



	@Override
	public List<InvoiceResponse> getInvoicesByBranchAndPay(Long id, boolean paid) {
		Branch branch =branchRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Branch does not exists"));
		return invoiceRepository.findByServiceRequest_Branch_IdAndPaid(id, paid).stream()
				.map(saved -> new InvoiceResponse(saved.getId(),
						saved.getAmount(),
						saved.isPaid(),
						saved.getGeneratedAt(),
						saved.getServiceRequest().getBranch().getId())).collect(Collectors.toList());
	}
	
	
	
	
	

}
