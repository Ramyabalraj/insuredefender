  
package com.insure.demo.service;

import java.util.*;  

import javax.transaction.Transactional;

import com.insure.demo.entity.User;
import com.insure.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public User create(User user) {
   
String email=user.getUserEmail();
 if((email=="")){

        }
        else{
        Boolean e=  userRepository.existsByuserEmail(email);
       if(e == false){
      
      return userRepository.save(user);
        }
        }
     System.out.println("Already exists");
    return null;
    
     }
 
public User login(User user) {
   String email=user.getUserEmail();
   String name=user.getUserName();
 if((email=="")&& (name=="")){

        }
        else{
        Boolean e=  userRepository.existsByuserEmail(email);
          Boolean b=  userRepository.existsByuserName(name);
       if((e == true)&&(b == true)){
      
      return user;
        }
        }
     System.out.println("Invalid");
    return null;
}
 

    public List<User> get() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User update(Long id,User user) {
        String email=user.getUserEmail();
        if(email=="" && user.getUserName() ==""){

        }
        else{
         Boolean e=  userRepository.existsByuserEmail(email);
         if(e == false){
         User t = userRepository.getOne(id);
         t.setUserName(user.getUserName());
         t.setUserEmail(user.getUserEmail());
        return userRepository.save(t);
        }
            }
  
        System.out.println("Already exists...");
        return null;
       
       
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

   
	public int saveExcelData(List<User> invoices) {
		invoices = userRepository.saveAll(invoices);
		return invoices.size();
	}

}