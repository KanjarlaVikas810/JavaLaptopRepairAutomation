package com.ey.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.entity.Invoice;
import com.ey.entity.ServiceRequest;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>{
	Optional<Invoice> findByServiceRequest(ServiceRequest serviceRequest);
	boolean existsByServiceRequest(ServiceRequest serviceRequest);
	List<Invoice> findByServiceRequest_Branch_Id(Long id);
	List<Invoice> findByServiceRequest_Branch_IdAndPaid(Long id,boolean paid);
}
