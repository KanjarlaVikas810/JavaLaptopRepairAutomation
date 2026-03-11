package com.ey.dto;

import java.time.LocalDateTime;

public class InvoiceResponse {
	
	private Long id;
	private double amount;
	private boolean paid;
	private LocalDateTime generatedAt;
	private Long BranchId;
	
	
	public InvoiceResponse(Long id, double amount, boolean paid, LocalDateTime generatedAt, Long branchId) {
		super();
		this.id = id;
		this.amount = amount;
		this.paid = paid;
		this.generatedAt = generatedAt;
		BranchId = branchId;
	}
	public Long getBranchId() {
		return BranchId;
	}
	public void setBranchId(Long branchId) {
		BranchId = branchId;
	}
	public Long getId() {
		return id;
	}
	public double getAmount() {
		return amount;
	}
	public boolean isPaid() {
		return paid;
	}
	public LocalDateTime getGeneratedAt() {
		return generatedAt;
	}
	
	

}
