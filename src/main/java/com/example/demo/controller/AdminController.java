package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.AdminServices;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminServices adminServices;
	
	 @PostMapping("/Addcategories")
	    public ResponseEntity<Object> createCategory(@RequestBody CategoryEntity category) {
	       
	        adminServices.AddCategory(category);
	        System.out.println("added category"+category.getName());
	        return ResponseEntity.ok("added");
	        
	        
	    }
	
 
	 @PostMapping("/register")
	 public ResponseEntity<Object> CreateAdmin(@RequestBody UserEntity userEntity){
		 
		 adminServices.AddAdmin(userEntity);
		 System.out.println("admin added");
		// ResponseEntity.ok().body("Registration Successful");
		 return ResponseEntity.ok().body("added");
	 }
	 
	 
	 @GetMapping("/getCategories")
	 public ResponseEntity<List<CategoryEntity>>getMethodName() {
		 System.out.println("gettimg categories");
		    List<CategoryEntity> categories = adminServices.getCategories();
		    return ResponseEntity.ok(categories);  // This should return a JSON list
		}
	 
	 @PostMapping("/addProduct")
	 public ResponseEntity<?> addProduct(@RequestPart ProductEntity productEntity,@RequestPart MultipartFile multipartFile) throws IOException{
		 adminServices.addProduct(productEntity,multipartFile);
		 return ResponseEntity.ok().body("added");
	 }
	 
}
	 

