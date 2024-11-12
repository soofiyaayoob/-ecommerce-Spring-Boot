package com.example.demo.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;

import com.example.demo.repositry.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService{
	
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private jwtServiceTaamsmaak jwtservicetaamsmaak;
	
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
	    
	    System.out.println("saving...");

	   
	    return userRepo.save(user);
	}


		public List<UserEntity> getAllUsers() {
			
			return userRepo.findAll();
		}
	}

	

