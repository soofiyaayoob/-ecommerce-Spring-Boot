package com.example.demo.Service;

import java.util.Collection;
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

	    // Save and return the user entity
	    return userRepo.save(user);
	}
//	  public UserEntity getUserFromSession(HttpSession session) {
//		  
//	        return (UserEntity) session.getAttribute("user");
//	    }
//	  public void saveUserInSession(HttpSession session, UserEntity user) {
//	        session.setAttribute("user", user);
//	    }
//	 
//	  public Authentication verify(UserEntity user) {
//		    try {
//		        // Create an Authentication object with the user's credentials
//		        Authentication authentication = 
//		            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//
//		        // Authenticate the user
//		        Authentication result = authenticationManager.authenticate(authentication);
//
//		        // Set the authentication in the security context
//		        SecurityContextHolder.getContext().setAuthentication(result);
//		        
//		        
//
//		        // Return the authenticated user object
//		        return result;
//		    } catch (AuthenticationException e) {
//		        // Return null if authentication fails
//		        return null;
//		    }
		  
//		  UsernamePasswordAuthenticationToken authentication = 
//			        new UsernamePasswordAuthenticationToken(user, null, null);
//			    
//			    // Set the Authentication object in SecurityContext
//			    SecurityContextHolder.getContext().setAuthentication(authentication);
//				return authentication;
//		}

	  public ResponseEntity<Map<String, Object>> authenticateAndRespond(UserEntity user, HttpServletRequest request, HttpServletResponse response) {
		  System.out.println(user.getPassword());
		  //boolean matches = passwordEncoder.matches(user.getPassword(), storedEncodedPassword);
		 // System.out.println("Password matches: " + matches);
	        try {
	            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
	            Authentication authentication = authenticationManager.authenticate(authenticationToken);
	            if (!authentication.isAuthenticated()) {
	            	
	                return ResponseEntity.status(401).body(Map.of("error", "Authentication failed"));
	            }

	            // Generate JWT and set in a secure HTTP-only cookie
	            String jwt = jwtservicetaamsmaak.generateToken(authentication.getName());
	            System.out.println("token genertaed");
	            ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
	                    .httpOnly(true)
	                    .path("/")
	                    .maxAge(7 * 24 * 60 * 60)
	                    .secure(true)
	                    .build();
	            response.addHeader("Set-Cookie", cookie.toString());
	            System.out.println("setted in cookie");

	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            System.out.println("AUTHENTICATED SETTED");

	            // Return the appropriate redirect URL based on roles
	            String redirectUrl = determineRedirectUrl(authentication);
	            return ResponseEntity.ok(Map.of("redirectUrl", redirectUrl));
	        } catch (AuthenticationException e) {
	            return ResponseEntity.status(500).body(Map.of("error", "Authentication exception occurred"));
	        }
	    }

	    public String determineRedirectUrl(Authentication authentication) {
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	            return "http://localhost:5500/adminpanel";
	        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
	            return "http://localhost:5500/home";
	        }
	        return "http://localhost:5500/login?error=true";
	    }
	}

	

