package com.insure.demo.repository;

import com.insure.demo.entity.User;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TeamRepository
 */
public interface UserRepository extends JpaRepository<User,Long>{
 boolean existsByuserEmail(String email);
boolean existsByuserName(String name);
    
}