package com.kgisl.demo.service;

import java.util.List;

import com.kgisl.demo.entity.Admin;

public interface AdminService {

	public Admin create(Admin admin);
    public List<Admin> get();
    public Admin findById(Long id);
    public Admin update(Long id,Admin admin);
    public void deleteById(Long id);
    public Admin login(Admin admin);
}
