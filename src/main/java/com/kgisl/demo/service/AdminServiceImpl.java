package com.kgisl.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgisl.demo.entity.Admin;
import com.kgisl.demo.repository.AdminRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository;

	@Override
	public Admin create(Admin admin) {
		String email = admin.getAdminEmail();
		if ((email == "")) {
			admin.setMessage("invalid");
		} else {
			Boolean e = adminRepository.existsByAdminEmail(email);
			if (e == false) {
				admin.setMessage("valid");
				return adminRepository.save(admin);
			}
		}
		System.out.println("Already exists");
		return admin;
	}

	@Override
	public List<Admin> get() {
		return adminRepository.findAll();
	}

	@Override
	public Admin findById(Long id) {
		return adminRepository.findById(id).orElse(null);
	}

	@Override
	public Admin update(Long id, Admin admin) {
		String email = admin.getAdminEmail();
		if (email == "" && admin.getAdmin() == "") {

		} else {
			Boolean e = adminRepository.existsByAdminEmail(email);
			if (e == false) {
				Admin t = adminRepository.getOne(id);
				t.setAdmin(admin.getAdmin());
				t.setAdminEmail(admin.getAdminEmail());
				return adminRepository.save(t);
			}
		}

		System.out.println("Already exists...");
		return null;
	}

	@Override
	public void deleteById(Long id) {
		adminRepository.deleteById(id);

	}

	@Override
	public Admin login(Admin admin) {
		String email = admin.getAdminEmail();
		List<Admin> ad = new ArrayList<>();
		if ((email == "")) {
			admin.setMessage("invalid login");	
		} else {
			Boolean e = adminRepository.existsByAdminEmail(email);
			//Boolean b = adminRepository.existsByAdmin(name);
			if ((e == true) ) {
                ad = adminRepository.findByAdminEmail(email);
                if(ad.size()>0) {
                	if(admin.getPassword().equals(ad.get(0).getPassword())) {
                	admin.setMessage("valid login");	
                	}
                	else {
                		admin.setMessage("invalid login");	
                	}
                }
				
			}
			else {
				admin.setMessage("invalid login");	
			}
		}
		System.out.println("Invalid");
		return admin;
	}
}
