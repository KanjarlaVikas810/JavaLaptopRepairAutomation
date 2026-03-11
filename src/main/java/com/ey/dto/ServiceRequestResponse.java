package com.ey.dto;

import java.time.LocalDateTime;

import com.ey.enums.ServiceStatus;

public class ServiceRequestResponse {
	
	private Long id;
	private String customerName;
	private Long customerId;
	private String laptopModel;
	private String issueDescription;
	private String diagnosis;
	private ServiceStatus status;
	private double cost;
	private LocalDateTime createdAt;
	private boolean invoice;
	private boolean invoiceGenerated;
	private Long invoiceId;
	
	private String imageUrl;
	
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	public ServiceRequestResponse(Long id, String customerName, Long customerId, String laptopModel,
			String issueDescription, String diagnosis, ServiceStatus status, double cost, LocalDateTime createdAt,
			boolean invoice, boolean invoiceGenerated, Long invoiceId, String imageUrl, LocalDateTime deliveryDate) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.customerId = customerId;
		this.laptopModel = laptopModel;
		this.issueDescription = issueDescription;
		this.diagnosis = diagnosis;
		this.status = status;
		this.cost = cost;
		this.createdAt = createdAt;
		this.invoice = invoice;
		this.invoiceGenerated = invoiceGenerated;
		this.invoiceId = invoiceId;
		this.imageUrl = imageUrl;
		this.deliveryDate = deliveryDate;
	}
	public ServiceRequestResponse(Long id, String customerName, Long customerId, String laptopModel,
			String issueDescription, String diagnosis, ServiceStatus status, double cost, LocalDateTime createdAt,
			boolean invoice, boolean invoiceGenerated, Long invoiceId, LocalDateTime deliveryDate) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.customerId = customerId;
		this.laptopModel = laptopModel;
		this.issueDescription = issueDescription;
		this.diagnosis = diagnosis;
		this.status = status;
		this.cost = cost;
		this.createdAt = createdAt;
		this.invoice = invoice;
		this.invoiceGenerated = invoiceGenerated;
		this.invoiceId = invoiceId;
		this.deliveryDate = deliveryDate;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public boolean isInvoiceGenerated() {
		return invoiceGenerated;
	}
	public void setInvoiceGenerated(boolean invoiceGenerated) {
		this.invoiceGenerated = invoiceGenerated;
	}
	public boolean isInvoice() {
		return invoice;
	}
	public void setInvoice(boolean invoice) {
		this.invoice = invoice;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	private LocalDateTime deliveryDate;
	
	public ServiceRequestResponse(Long id, String laptopModel, String issueDescription, String diagnosis,
			ServiceStatus status, double cost, LocalDateTime createdAt, LocalDateTime deliveryDate) {
		super();
		this.id = id;
		this.laptopModel = laptopModel;
		this.issueDescription = issueDescription;
		this.diagnosis = diagnosis;
		this.status = status;
		this.cost = cost;
		this.createdAt = createdAt;
		this.deliveryDate = deliveryDate;
	}
	public Long getId() {
		return id;
	}
	public String getLaptopModel() {
		return laptopModel;
	}
	public String getIssueDescription() {
		return issueDescription;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public ServiceStatus getStatus() {
		return status;
	}
	public double getCost() {
		return cost;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}
	
	
	

}
