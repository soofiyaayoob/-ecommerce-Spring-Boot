package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Aspect.EmailService;
import com.example.demo.Aspect.OtpService;
import com.example.demo.CustomHandler.SucessHandler;
import com.example.demo.Service.UserService;
import com.example.demo.model.UserEntity;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
public class Homecontroller {
	
	private static final Logger logger = LoggerFactory.getLogger(Homecontroller.class);
	
	@Autowired
	OtpService otpService;
	
	@Autowired
	EmailService emailService;
	
	 @Autowired
	    private AuthenticationManager authenticationManager; // Make sure to configure this bean

	 
	
	
	
	@Autowired
	UserService userService;
	
//	@PostMapping("/api/register")
//	public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserEntity userDto) {
//	    Map<String, Object> response = new HashMap<>();
//	    
//	    
//	    response.put("success", true);
//	    
//	    return ResponseEntity.ok(response);
//	}
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserEntity userDto,HttpServletResponse response,HttpServletRequest request) {
		 Map<String, Object> responses = new HashMap<>();
		 
		 
		 

		    HttpSession session = request.getSession(); // Creates a session if it doesn't exist
		    session.setAttribute("user", userDto);
		    session.setAttribute("initialized", true);

		    System.out.println("Session ID (for cookie): " + session.getId());

		  

		    try {
		        System.out.println("Received UserEntity: " + userDto);
		        String username = userDto.getUsername(); 
		        String email = userDto.getEmail(); 

		        // Generate OTP
		        String otp = otpService.generateOtp(username);
		        System.out.println("Generated OTP: " + otp);

		        // Send OTP via email
		        emailService.sendOtpEmail(email, otp);

		        responses.put("success", true);
		        responses.put("message", "OTP sent to your email. Please verify to complete registration.");
		    } catch (Exception e) {
		        System.out.println("Exception occurred: " + e.getMessage());
		        responses.put("success", false);
		        responses.put("message", "Registration failed: " + e.getMessage());
		    }
		    return ResponseEntity.ok(responses);
		}
	
	
	

	@PostMapping("/verification")
	public ResponseEntity<Map<String, Object>> postMethodName(@RequestBody Map<String, String> requestBody, HttpServletRequest request,HttpServletResponse response) {
		  String code = requestBody.get("code");
		    System.out.println("Received code: " + code);

		    Map<String, Object> responseMap = new HashMap<>();
		    

		    try {
		       
		       

		        // Retrieve cookies for debugging purposes
		        Cookie[] cookies = request.getCookies();
		        if (cookies != null) {
		            for (Cookie cookie : cookies) {
		                System.out.println("Cookie: " + cookie.getName() + " = " + cookie.getValue());
		            }
		        } else {
		            System.out.println("Cookie is null");
		        }

		        // Retrieve the session and user data from it
		        HttpSession session = request.getSession(false);
		        if (session == null) {
		            responseMap.put("success", false);
		            responseMap.put("message", "Session not found.");
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
		        }

		        UserEntity userDto = (UserEntity) session.getAttribute("user"); // Retrieve user data from session

		        if (userDto == null) {
		            responseMap.put("success", false);
		            responseMap.put("message", "No user data found in session.");
		            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
		        }

		        // Validate OTP
		        String username = userDto.getUsername();
		        boolean isValid = otpService.validateOtp(username, code);

		        if (isValid) {
		            // Register the user
		            userService.registerUserEntity(userDto);
		            System.out.println("User registered successfully.");

		            responseMap.put("success", true);
		            responseMap.put("message", "User registered successfully.");
		            return ResponseEntity.ok(responseMap);
		        } else {
		            // Return an error if OTP is invalid
		            responseMap.put("success", false);
		            responseMap.put("message", "Invalid OTP code.");
		            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
		        }

		    } catch (Exception e) {
		        // Handle any exceptions and return an error response
		        responseMap.put("success", false);
		        responseMap.put("message", "An error occurred during registration.");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
		    }
		} 	
	    
		        
		       // }
		   // userDto.setPassword(originalPassword);
		   //System.out.println(userDto.getPassword());

		    //return userService.authenticateAndRespond(userDto, request, response);

		   
	
	


	  @PostMapping("/perform_login")
	    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserEntity userDto,
	                                                            HttpServletRequest request,
	                                                            HttpServletResponse response) {
		  
		   // Authentication authentication = userService.verify(userDto);
		    
		    return userService.authenticateAndRespond(userDto, request, response);
		    
	  }
	}


	
	
	

