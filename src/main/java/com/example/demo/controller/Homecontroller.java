package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Aspect.EmailService;
import com.example.demo.Aspect.OtpService;
import com.example.demo.Service.UserService;
import com.example.demo.model.UserEntity;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Homecontroller {
	
	@Autowired
	OtpService otpService;
	
	@Autowired
	EmailService emailService;
	
	
	
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
	public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserEntity userDto,HttpSession session) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        // Print received user entity for debugging
	        System.out.println("Received UserEntity: " + userDto);

	        // Extract username and email from userDto
	        String username = userDto.getUsername(); // Assuming UserEntity has a getUsername method
	        String email = userDto.getEmail(); // Assuming UserEntity has a getEmail method

	        // Generate OTP
	        String otp = otpService.generateOtp(username);
System.out.println("geneated");
	        // Send OTP via email
	     //   emailService.sendOtpEmail(email, otp);
	        System.out.println("failed not from here");

	        session.setAttribute("tempUserData", userDto);
	        System.out.println("data added to session");

	        response.put("success", true);
	        response.put("message", "OTP sent to your email. Please verify to complete registration.");
	    } catch (Exception e) {
	        System.out.println("Exception occurred: " + e.getMessage());
	        response.put("success", false);
	        response.put("message", "Registration failed: " + e.getMessage());
	    }
	    return ResponseEntity.ok(response);
	}


	@GetMapping("/login")
	public String apiEndpoint() {
	    String email = "soofiyaayoob786@gmail.com";
	    emailService.sendOtpEmail(email);
	    return "API is accessible";
	}
	
	@GetMapping("/test")
	public String getMethodName() {
		 String email = "soofiyaayoob786@gmail.com";
		    try {
		        emailService.sendOtpEmail(email);
		    } catch (Exception e) {
		        e.printStackTrace(); // Log the exception
		        return "Error: " + e.getMessage();
		    }
		    return "hello";
	}
	

	    
	    
	    
	}

//
//@GetMapping("/verify")
//public ResponseEntity<Map<String, Object>> verifyOtp(HttpSession session) {
//    Map<String, Object> response = new HashMap<>();
//    
//    // Retrieve user data from session
//    UserEntity userDto = (UserEntity) session.getAttribute("tempUserData");
//    
//    if (userDto != null) {
//        // Proceed with OTP verification logic
//        response.put("success", true);
//        response.put("message", "User data retrieved successfully.");
//    } else {
//        response.put("success", false);
//        response.put("message", "No user data found in session.");
//    }
//    
//    return ResponseEntity.ok(response);
//}

	
	
	

