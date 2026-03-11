package com.ey.serviceInterface;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ey.dto.DiagnosisUpdateDto;
import com.ey.dto.InvoiceResponse;
import com.ey.dto.ServiceRequestCreateDto;
import com.ey.dto.ServiceRequestResponse;
import com.ey.entity.Invoice;
import com.ey.enums.ServiceStatus;

public interface ServiceRequestServiceInterface {

	ServiceRequestResponse createRequest(ServiceRequestCreateDto request, MultipartFile image, String string);

	ServiceRequestResponse updateDiagnosis(Long id, DiagnosisUpdateDto request);

	ServiceRequestResponse updatestatus(Long id, ServiceStatus status);

	InvoiceResponse generateInvoice(Long id, String name);

	List<ServiceRequestResponse> getServices(String name);

	List<ServiceRequestResponse> getCustomerService(Long id, String name);

	InvoiceResponse invoicePay(Long id);

	List<ServiceRequestResponse> getCustomerServices(String email);

	ServiceRequestResponse approval(Long requestId, boolean approved, String name);

	List<InvoiceResponse> getInvoices();


	

}
