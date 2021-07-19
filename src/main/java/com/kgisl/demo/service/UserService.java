package com.kgisl.demo.service;
import java.util.List;

import com.kgisl.demo.entity.User;


public interface UserService {

    public User create(User user);
    public List<User> get();
    public User findById(Long id);
    public User update(Long id,User user);
    public void deleteById(Long id);
    public User login(User user);
    public int saveExcelData(List<User> invoices);
}