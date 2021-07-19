package com.kgisl.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kgisl.demo.entity.Admin;
import com.kgisl.demo.entity.User;

public interface AdminRepository extends JpaRepository<Admin,Long>{
	 boolean existsByAdminEmail(String email);
	 boolean existsByAdmin(String name);
	 List<Admin> findByAdminEmail(String email);
}
