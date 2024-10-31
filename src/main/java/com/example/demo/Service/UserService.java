package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.repositry.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService{
	
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public UserEntity registerUserEntity(UserEntity user) {
		  // Check if password is provided
	    if (user.getPassword() == null || user.getPassword().isEmpty()) {
	        throw new IllegalArgumentException("Password cannot be empty");
	    }

	    // Encode password
	    user.setPassword(passwordEncoder.encode(user.getPassword()));

	    // Set user reference in each address if addresses exist
	    if (user.getAddresses() != null) {
	        user.getAddresses().forEach(address -> address.setUser(user));
	    }

	    // Save and return the user entity
	    return userRepo.save(user);
	}
	
	

	
	
	
}
