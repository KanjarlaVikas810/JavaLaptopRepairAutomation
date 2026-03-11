package com.ey.entity;

import java.time.LocalDateTime;

import com.ey.enums.ServiceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ServiceRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String laptopModel;
	
	@Column(columnDefinition="TEXT")
	private String issueDescription;
	
	private String diagnosis;
	
	@Enumerated(EnumType.STRING)
	private ServiceStatus status;
	
	private double cost;
	
	private LocalDateTime deliveryDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User customer;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User technician;
	
	
	
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name="branch_id",nullable=false)
	private Branch branch;
	
	private String imageUrl;
	
	



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public Branch getBranch() {
		return branch;
	}



	public void setBranch(Branch branch) {
		this.branch = branch;
	}



	public ServiceRequest() {
		super();
	}
	
	

	public ServiceRequest(String laptopModel, String issueDescription, ServiceStatus status,
			User customer, User technician, LocalDateTime createdAt) {
		super();
	
		this.laptopModel = laptopModel;
		this.issueDescription = issueDescription;
		this.status = status;
		this.customer = customer;
		this.technician = technician;
		this.createdAt = createdAt;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLaptopModel() {
		return laptopModel;
	}

	public void setLaptopModel(String laptopModel) {
		this.laptopModel = laptopModel;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public double getCost() {
		return cost;
	}



	public void setCost(double cost) {
		this.cost = cost;
	}



	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}



	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}



	public User getTechnician() {
		return technician;
	}



	public void setTechnician(User technician) {
		this.technician = technician;
	}
	
	

}
