package com.example.demo.Principle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.repositry.UserRepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserDetailsServiceTaamsmaak implements UserDetailsService{
	
	@Autowired
	UserRepo userRepo;
	
	 @Autowired
	    private HttpServletRequest request; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user=userRepo.findByUsername(username). orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		if(user==null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException("oh ithink you are not existed");
			
		}
//		  if (!user.isActive()) {
//	            // Set the error message in the session
//	            HttpSession session = request.getSession();
//	            session.setAttribute("error", "User account is disabled");
//	            System.out.println("isss activate ");
//
//	          
//	            throw new DisabledException("User account is disabled");
//	        }
	return new UserPrincipleTaamsmaak(user);
	}
	
	
	}

