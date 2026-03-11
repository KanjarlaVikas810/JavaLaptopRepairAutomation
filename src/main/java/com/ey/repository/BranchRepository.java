package com.ey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch,Long>{
	boolean existsByContact(String contact);
}
