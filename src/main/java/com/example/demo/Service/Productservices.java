package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ProductEntity;
import com.example.demo.model.SizeEntity;
import com.example.demo.repositry.SizeRepo;
import com.example.demo.repositry.productRepo;

@Service
public class Productservices {
	
	@Autowired
	productRepo productRepo;
	
	@Autowired
	SizeRepo sizeRepo;

	public ProductEntity addProduct(ProductEntity product) {
		
		return productRepo.save(product);
	}

	public void addSizes(SizeEntity productSize) {
		sizeRepo.save(productSize);
		
	}
	
	

}
