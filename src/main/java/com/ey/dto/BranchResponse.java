package com.ey.dto;

public class BranchResponse {
	
	private Long id;
	private String name;
	private String location;
	private String contact;
	public BranchResponse(Long id, String name, String location, String contact) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.contact = contact;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
}
