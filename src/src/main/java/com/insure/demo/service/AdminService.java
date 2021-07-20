package com.insure.demo.service;

import java.util.List;

import com.insure.demo.entity.Admin;

public interface AdminService {

	public Admin create(Admin admin);
    public List<Admin> get();
    public Admin findById(Long id);
    public Admin update(Long id,Admin admin);
    public void deleteById(Long id);
    public Admin login(Admin admin);
}
