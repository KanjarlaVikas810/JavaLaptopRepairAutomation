package com.ey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.entity.Branch;
import com.ey.entity.ServiceRequest;
import com.ey.entity.User;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest,Long>{
	
	List<ServiceRequest>findByCustomer(User customer);
	List<ServiceRequest> findByTechnician(User technician);
	List<ServiceRequest> findByBranch(Branch branch);

}
