package com.ey.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.entity.User;
import com.ey.enums.Role;

public interface UserRepository extends JpaRepository<User,Long>{
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	List<User> findByRole(Role role);

}
