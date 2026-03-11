package com.ey.dto;

public class TechnicianResponse {
	private Long id;
	private String email;
	private String name;
	private String phone;
	private Long branchId;
	public TechnicianResponse(Long id, String email, String name, String phone, Long branchId) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.phone = phone;

		this.branchId = branchId;
	}
	public Long getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}

	public Long getBranchId() {
		return branchId;
	}
	
	

}
