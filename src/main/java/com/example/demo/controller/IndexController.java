package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.UserService;
import com.example.demo.model.AddressEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repositry.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepo userRepo;
	
	 @GetMapping("/")
	    public String home() {
	        return "index"; // Redirects to index.html in the static folder
	    }
	 @GetMapping("/login")
	 public String loginPage() {
	     return "login"; // Return the template name (without .html)
	 }
	 
	 @GetMapping("/register")
	    public String showRegistrationPage(Model model) {
		 UserEntity user = new UserEntity();
	
		 user.getAddresses().add(new AddressEntity());  


		    model.addAttribute("user", user);
		    System.out.println("User addresses initialized with size: " + user.getAddresses().size());
	        return "register"; // Returns the register.ht
	        
	    }
//	 @GetMapping("/")
//	 public String showForm() {
//		 
//	        
//	        return "login"; // The name of the HTML template
//	    }
//	 
	 @PostMapping("/user/save")
	    public String saveUser(@ModelAttribute("userEntity") UserEntity userEntity, 
	                           @RequestParam String state, @RequestParam String city, @RequestParam String pincode) {
	        // Create and set the AddressEntity
		 System.out.println(userEntity.getFullName());
	        AddressEntity address = new AddressEntity();
	        address.setState(state);
	        address.setCity(city);
	        address.setPincode(pincode);
	        address.setUser(userEntity);  // Setting the relationship

	        userEntity.getAddresses().add(address);  // Adding the address to the user's address list
	        userService.registerUserEntity(userEntity); // Save the UserEntity which will cascade to save AddressEntity

	        return "redirect:/login"; // Redirect after successful save
	    }
	 
//	  @PostMapping("/perform_login")
//	    public String registerUser(@RequestBody UserEntity userDto,
//	                                                            HttpServletRequest request,
//	                                                            HttpServletResponse response) {
//		  
//		   // Authentication authentication = userService.verify(userDto);
//		    
//		    //userService.authenticateAndRespond(userDto, request, response);
//		    return("/");
//		    
//	  }
	 
	 @GetMapping("/admin")
	    public String showAdminPage() {
	        return "admin"; // Ensure "register.html" exists in the templates folder
	    }
	 
	 @GetMapping("/error")
	    public String showERrorPage() {
	        return "error"; // Ensure "register.html" exists in the templates folder
	    }
	 
	 @PostMapping("/register")
	    public String registerUser(@ModelAttribute UserEntity user) {
		 System.out.println("here register"+user.getUsername());
		 for (AddressEntity address : user.getAddresses()) {
		        address.setUser(user); // Set the user for each address
		    }
//		 userRepo.save(user);
	        userService.registerUserEntity(user);
	        
	        return "redirect:/login"; // Redirect to the login page after successful registration
	    }
}
