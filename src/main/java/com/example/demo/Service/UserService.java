package com.example.demo.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.example.demo.Principle.UserPrincipleTaamsmaak;

import com.example.demo.model.AddressEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repositry.AddressRepo;
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
	AddressRepo addressRepo;
	
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

@Transactional
		public List<UserEntity> getAllUsers() {
			
			return userRepo.findAll();
		}
		
		public Long getCurrentUserId() {
	        
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        
	        
	        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipleTaamsmaak) {
	           
	            UserPrincipleTaamsmaak userPrinciple = (UserPrincipleTaamsmaak) authentication.getPrincipal();
	            return userPrinciple.getUserid();  
	        }
	        
	        return null;  
	    }


		public List<AddressEntity> getAddressesByUserId() {
			
		Long userId=this.getCurrentUserId();
		List<AddressEntity> addressEntities = addressRepo.findByUserId(userId);
			return addressEntities;
		}
		   public void saveAddress(AddressEntity address) {
		        addressRepo.save(address);
		    }


		public UserEntity getUserByEmail(String email) {
			
			return userRepo.findByEmail(email);
		}


		public UserEntity getUserByToken(String token) {
			
			return userRepo.findByToken(token);
		}


		public void setPassword(UserEntity userByToken, String password) {
			userByToken.setPassword(passwordEncoder.encode(password));
			userByToken.setToken(null);
			userRepo.save(userByToken);
			
		}


		public void setToken(UserEntity userByEmail, String token) {
			userByEmail.setToken(token);
			userRepo.save(userByEmail);
			
		}

		public void toggleActive(Long id) {
			 UserEntity user = userRepo.findById(id)
          .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
			 user.setActive(!user.isActive());
			 userRepo.save(user);
}

		public UserEntity findUser() {
			long id=this.getCurrentUserId();
			UserEntity user=userRepo.findById(id).orElseThrow();
			return user;
		}

		public void updateUser(UserEntity user) {
			
			userRepo.save(user);
			
		}

		public void changePassword(String currentPassword, String newPassword) {
		    
	        UserEntity user = userRepo.findById(this.getCurrentUserId()).orElseThrow(() -> new RuntimeException("User not found"));

	        
	        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
	            throw new RuntimeException("Current password is incorrect");
	        }

	     
	        if (passwordEncoder.matches(newPassword, user.getPassword())) {
	            throw new RuntimeException("New password cannot be the same as the current password");
	        }

	       
	        user.setPassword(passwordEncoder.encode(newPassword));
	        userRepo.save(user);
	    }
		
		}
		
			
		


		
		
	

	

