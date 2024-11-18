package com.example.demo.Service;

import java.time.LocalDateTime;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CartEntity;
import com.example.demo.model.CartItemEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repositry.CartItemRepo;
import com.example.demo.repositry.UserRepo;
import com.example.demo.repositry.cartRepo;
import com.example.demo.repositry.productRepo;

@Service
public class CartService {
	
	@Autowired
	productRepo productRepo;
	
	@Autowired
	cartRepo cartRepo;
	
	@Autowired
	CartItemRepo cartItemRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepo userRepo;
	
	
	
	public boolean addProductToCart(Long productId) throws AttributeNotFoundException {
	    Long userId = userService.getCurrentUserId(); 
	    CartEntity cart = cartRepo.findByUserId(userId);

	    
	    if (cart == null) {
	        cart = new CartEntity();
	        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	        cart.setUser(user);
	        cart.setCreatedAt(LocalDateTime.now());
	        cartRepo.save(cart);
	    }

	   
	    ProductEntity product = productRepo.findById(productId).orElseThrow(() -> new AttributeNotFoundException("Product not found"));

	  
	    CartItemEntity existingCartItem = cartItemRepo.findByCartAndProduct(cart, product);
	    if (existingCartItem != null) {
	        return false; 
	    }

	  
	    CartItemEntity cartItem = new CartItemEntity();
	    cartItem.setCart(cart);
	    cartItem.setProduct(product);
	    cartItemRepo.save(cartItem); 

	    return true; 
	}
}
