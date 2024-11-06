package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.CategoryEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.UserEntity.Role;
import com.example.demo.repositry.CategoryRepo;
import com.example.demo.repositry.UserRepo;

@Service
public class AdminServices {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void AddCategory(CategoryEntity category) {
		categoryRepo.save(category);
		
		
	}

	public void AddAdmin(UserEntity userEntity) {
		
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

	    // Set user reference in each address if addresses exist
	    if (userEntity.getAddresses() != null) {
	        userEntity.getAddresses().forEach(address -> address.setUser(userEntity));
	    }
	    
	    System.out.println("saving...");
		
		userEntity.setRole(Role.ADMIN);
		userRepo.save(userEntity);
		
	}
	
	

}
