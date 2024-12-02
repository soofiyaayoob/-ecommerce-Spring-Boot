package com.example.demo.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.AddressEntity;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.UserEntity.Role;
import com.example.demo.repositry.CategoryRepo;
import com.example.demo.repositry.UserRepo;
import com.example.demo.repositry.productRepo;

import jakarta.transaction.Transactional;

@Service
public class AdminServices {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	productRepo productRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void AddCategory(CategoryEntity category) {
		System.out.println("Category name before save: " + category.getName());
		categoryRepo.save(category);
		
		
	}
     @Transactional
	public void AddAdmin(UserEntity userEntity) {
		
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

	    // Set user reference in each address if addresses exist
	    if (userEntity.getAddresses() != null) {
	        userEntity.getAddresses().forEach(address -> address.setUser(userEntity));
	    }
	    
	    System.out.println("saving...");
		
		userEntity.setRole(Role.ADMIN);
		userRepo.save(userEntity);
		
	}

	public List<CategoryEntity> getCategories() {
		
		return categoryRepo.findAll();
	}

	public void addProduct(ProductEntity productEntity, MultipartFile imageFile) throws IOException {
		productEntity.setImageName(imageFile.getOriginalFilename());
		productEntity.setImageData(imageFile.getBytes());
		productRepo.save(productEntity);
	}
	public void AddRestaurant(UserEntity userEntity) {
	userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
	 for (AddressEntity address : userEntity.getAddresses()) {
         address.setUser(userEntity);
     }
	userEntity.setRole(Role.RESTAURANT);
	userRepo.save(userEntity);
		
	}
	
	

}
