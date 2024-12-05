package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.utilty.Commonutil;
import com.example.demo.model.CategoryEntity;
import com.example.demo.repositry.CategoryRepo;

import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	Commonutil commonutil;
	
	public Boolean existCategoryByname(String name) {
		return categoryRepo.existsByName(name);
	}

	public void SaveCategory(CategoryEntity categoryEntity) {
		categoryRepo.save(categoryEntity);
		return;
		
	}
@Transactional
	public List<CategoryEntity> getAllcategries() {
		  List<CategoryEntity> categories = categoryRepo.findAll();
		 for (CategoryEntity category : categories) {
		        commonutil.convertImageDataToBase64(category);
		    }
		return categories;
		
	}

	public CategoryEntity getCategoryById(Long categoryId) {
		 try {
		        // Attempt to find the category by ID
		       CategoryEntity categoryEntity=  categoryRepo.findById(categoryId)
		                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
		       
		      return (CategoryEntity) commonutil.convertImageDataToBase64(categoryEntity);
		         
		         
		   
		    } catch (Exception e) {
		       
		        System.err.println("Unexpected error occurred while finding category: " + e.getMessage());
		        throw new RuntimeException("Unexpected error occurred", e);
		    }
	}

	public void deleteCategoryById(Long id) {
		categoryRepo.deleteById(id);
		
	}

	public boolean updateCategory(Long id, String categoryName, MultipartFile file) {
		  try {
	            // Fetch category by ID
	            CategoryEntity categoryEntity = this.getCategoryById(id);

	            // Check if category already exists
	            boolean categoryExists = this.existCategoryByname(categoryName);
	            if (categoryExists) {
	                return false; 
	            }

	           
	            if (!file.isEmpty()) {
	                String image = file.getOriginalFilename();
	                byte[] data = file.getBytes();
	                categoryEntity.setImageName(image);
	                categoryEntity.setImageData(data);
	            }

	            
	            categoryEntity.setName(categoryName);

	          
	            categoryRepo.save(categoryEntity);
	            return true;
	        } catch (IOException e) {
	            throw new IOException("Error processing file", e);
	        } catch (Exception e) {
	            throw new RuntimeException("An error occurred while updating the category", e);
	        }
	    }
		
	}

	
	

	
	


