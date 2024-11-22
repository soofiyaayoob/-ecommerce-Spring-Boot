package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Aspect.EmailService;
import com.example.demo.Aspect.OtpService;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.UserService;
import com.example.demo.model.AddressEntity;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repositry.UserRepo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	OtpService otpService;
	
	
	 @GetMapping("/login")
	 public String loginPage() {
	     return "login"; 
	 }
	 
	 @GetMapping("/register")
	    public String showRegistrationPage(Model model) {
		 UserEntity user = new UserEntity();
	
		 user.getAddresses().add(new AddressEntity());  


		    model.addAttribute("user", user);
		   // System.out.println("User addresses initialized with size: " + user.getAddresses().size());
	        return "register"; // Returns the register.ht
	        
	    }
	 
	 @PostMapping("/user/save")
	    public String saveUser(@ModelAttribute("userEntity") UserEntity userEntity, 
	                           @RequestParam String state, @RequestParam String city, @RequestParam String pincode) {
	        // Create and set the AddressEntity
		 System.out.println(userEntity.getFullName());
	        AddressEntity address = new AddressEntity();
	        address.setState(state);
	        address.setCity(city);
	        address.setPincode(pincode);
	        address.setUser(userEntity);  

	        userEntity.getAddresses().add(address);  
	        userService.registerUserEntity(userEntity); 

	        return "redirect:/login";
	    }
	 

	 
	 @GetMapping("/error")
	    public String showERrorPage() {
	        return "error";
	    }
	 
	 @PostMapping("/register")
	    public String registerUser(@ModelAttribute UserEntity user,HttpSession session) {
		 System.out.println("here register"+user.getUsername());
		 for (AddressEntity address : user.getAddresses()) {
		        address.setUser(user);
		    }
		
		    session.setAttribute("user", user);
		    session.setAttribute("initialized", true);

		    System.out.println("Session ID (for cookie): " + session.getId());

		  

		    try {
		        System.out.println("Received UserEntity: " + user);
		        String username = user.getUsername(); 
		        String email = user.getEmail(); 

		     
		        String otp = otpService.generateOtp(username);
		        System.out.println("Generated OTP: " + otp);

		      
		        emailService.sendOtpEmail(email, otp);
		    }catch (Exception e) {
				
			}
	        return "redirect:/Otp";
	    }
	 
	 @GetMapping("/Otp")
	 public String getMethodName() {
	 	return "/otp";
	 }
	 
	  @PostMapping("/verify")
	    public String verifyOtp(@RequestParam("code1") String code1,
	                            @RequestParam("code2") String code2,
	                            @RequestParam("code3") String code3,
	                            @RequestParam("code4") String code4,
	                            @RequestParam("code5") String code5,
	                            @RequestParam("code6") String code6,
	                            Model model,HttpSession session) {
	        
	       
	        String otpCode = code1 + code2 + code3 + code4 + code5 + code6;
	    
	        
	        try {
	        	 UserEntity userDto = (UserEntity) session.getAttribute("user");
	        	 String username=userDto.getUsername();
	        	
	         
	            boolean isValid = otpService.validateOtp(username, otpCode);
	         

	            if (isValid) {
	            	
	            	userService.registerUserEntity(userDto);
	              
	                model.addAttribute("message", "OTP Verified Successfully!");
	                return"redirect:/login";
	            } else {
	               
	                model.addAttribute("message", "Invalid OTP Code.");
	                return "otp";  
	            }

	        } catch (Exception e) {
	        
	            model.addAttribute("message", "An error occurred. Please try again.");
	            return "register";  
	        }
	    }
	  
	  @GetMapping("/resetOtp")
	  public void OtpReset(HttpSession session) {
		  UserEntity user=(UserEntity) session.getAttribute("user");
         String username=user.getUsername();
         otpService.generateOtp(username);
	  
	  }
	  

	    }
	
		    
		      

	
		
		
