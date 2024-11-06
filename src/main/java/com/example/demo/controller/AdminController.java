package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.AdminServices;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.UserEntity;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminServices adminServices;
	
	 @PostMapping("/Addcategories")
	    public ResponseEntity<Object> createCategory(@RequestBody CategoryEntity category) {
	       
	        adminServices.AddCategory(category);
	        System.out.println("added category");
	        return ResponseEntity.ok("added");
	        
	        
	    }
	 
	 @PostMapping("/register")
	 public ResponseEntity<Object> CreateAdmin(@RequestBody UserEntity userEntity){
		 
		 adminServices.AddAdmin(userEntity);
		 System.out.println("admin added");
		// ResponseEntity.ok().body("Registration Successful");
		 return ResponseEntity.ok().body("added");
	 }
	 

}
