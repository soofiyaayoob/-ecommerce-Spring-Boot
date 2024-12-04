package com.example.demo.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.CategoryEntity;
import com.example.demo.model.ProductEntity;

import com.example.demo.repositry.CategoryRepo;

import com.example.demo.repositry.productRepo;

import jakarta.transaction.Transactional;

@Service
public class Productservices {
	
	@Autowired
	productRepo productRepo;
	
	@Autowired
	CategoryService categoryService;
	

	
	@Autowired
	CategoryRepo categoryRepo;

	public ProductEntity addProduct(ProductEntity product, MultipartFile imageFile) throws IOException {
		
		  product.setImageName(imageFile.getOriginalFilename());
           product.setImageData(imageFile.getBytes());
		return productRepo.save(product);
	}

	
	public ProductEntity GetById(Long productId) {
		
		return productRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("invalid prodict id"+productId));
	}

	@Transactional
	public List<ProductEntity> getAllProducts() {
		
		
		List<ProductEntity> products= productRepo.findAll();
		 for (ProductEntity product : products) {
	            if (product.getImageData() != null) {
	            	byte[] imageData = product.getImageData();  

	                String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageData);
	                product.setImageBase64(base64Image);
	                
	            }
	            }
		 return products;
		 
	}
	@Transactional
	public List<ProductEntity>LatestProduct(){
	List<ProductEntity> products=productRepo.findTop5ByOrderByIdDesc();
	products.forEach(product ->{
		byte[] imgebyte= product.getImageData();
		   System.out.println("Image size: " + imgebyte.length);
		String base64="data:image/png;base64,"+Base64.getEncoder().encodeToString(imgebyte);
		product.setImageBase64(base64);

		
	});
	return products;

	

}

//	@Transactional
//	public List<CategoryEntity> getAllCategories() {
//	    List<CategoryEntity> categories = categoryRepo.findAll();
//	    categories.forEach(category -> {
//	       
//	        
//	        
//	        String base64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(category.getImageData());
//	        category.setImageBase64(base64);
//	        
//	    });
//	    return categories;
//	}


	@Transactional
	public List<ProductEntity> getProductsByCategory(Long id) {
		
		List<ProductEntity> products=productRepo.findByCategoryId(id);  
		
		products.forEach(product ->{
			byte[] imgebyte= product.getImageData();
			   System.out.println("Image size: " + imgebyte.length);
			String base64="data:image/png;base64,"+Base64.getEncoder().encodeToString(imgebyte);
			product.setImageBase64(base64);

			
		});
		return products;

	}


	public void updateproduct(ProductEntity productEntity, MultipartFile image, Long categoryId) throws IOException {
		String productImage=image.getOriginalFilename();
		CategoryEntity categoryEntity=categoryService.getCategoryById(categoryId);
		  ProductEntity product = new ProductEntity();
		    
		     product.setCategory(categoryEntity); 
		     product.setImageName(productImage);
		     product.setImageData(image.getBytes());
		     productRepo.save(product);
		
	}


	public void imageBase64(ProductEntity product) {
		String base64="data:image/png;base64,"+Base64.getEncoder().encodeToString(product.getImageData());
		product.setImageBase64(base64);
	}


	public void updateProduct(ProductEntity product, MultipartFile imageFile) throws IOException {
		
		  ProductEntity existingProduct = productRepo.findById(product.getId())
		            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + product.getId()));
		 if (!imageFile.isEmpty()) {
		        product.setImageData(imageFile.getBytes());
		        product.setImageName(imageFile.getOriginalFilename());
		    }else {
		        // Retain the old image if no new image is uploaded
		        product.setImageData(existingProduct.getImageData());
		        product.setImageName(existingProduct.getImageName());
		    }
		
		productRepo.save(product);
		
	}


	public boolean deleteById(Long id) {
		productRepo.deleteById(id);
		return false;
	}





}
