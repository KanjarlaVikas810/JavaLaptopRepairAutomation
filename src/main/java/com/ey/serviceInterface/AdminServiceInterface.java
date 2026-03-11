package com.ey.serviceInterface;

import java.util.List;

import com.ey.dto.BranchDto;
import com.ey.dto.BranchResponse;
import com.ey.dto.CustomerResponse;
import com.ey.dto.InvoiceResponse;
import com.ey.dto.TechnicianRegister;
import com.ey.dto.TechnicianResponse;

public interface AdminServiceInterface {

	BranchResponse registerBranch(BranchDto request);

	List<BranchResponse> getAllBranches();

	void deleteBranch(Long id);

	TechnicianResponse registerTechnician(TechnicianRegister request);

	List<TechnicianResponse> getAllTechnician();

	TechnicianResponse getTechnician(Long id);

	String deleteTechnician(Long id);

	List<CustomerResponse> getAllCustomers();

	List<InvoiceResponse> getInvoicesByBranch(Long id);

	List<InvoiceResponse> getInvoicesByBranchAndPay(Long id, boolean paid);

}
