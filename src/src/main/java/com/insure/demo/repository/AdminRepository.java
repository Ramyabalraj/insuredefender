package com.insure.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.insure.demo.entity.Admin;
import com.insure.demo.entity.User;

public interface AdminRepository extends JpaRepository<Admin,Long>{
	 boolean existsByAdminEmail(String email);
	 boolean existsByAdmin(String name);
	 List<Admin> findByAdminEmail(String email);
}
