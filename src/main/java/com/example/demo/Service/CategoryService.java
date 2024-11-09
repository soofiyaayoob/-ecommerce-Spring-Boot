package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CategoryEntity;
import com.example.demo.repositry.CategoryRepo;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	public Boolean existCategory(String name) {
		return categoryRepo.existsByName(name);
	}

	public void SaveCategory(CategoryEntity categoryEntity) {
		categoryRepo.save(categoryEntity);
		return;
		
	}

	public List<CategoryEntity> getAllcategries() {
		return categoryRepo.findAll();
		
	}

	public CategoryEntity findCayegory(Long categoryId) {
		 try {
		        // Attempt to find the category by ID
		        return categoryRepo.findById(categoryId)
		                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
		   
		    } catch (Exception e) {
		       
		        System.err.println("Unexpected error occurred while finding category: " + e.getMessage());
		        throw new RuntimeException("Unexpected error occurred", e);
		    }
	}
	

	
	

}
