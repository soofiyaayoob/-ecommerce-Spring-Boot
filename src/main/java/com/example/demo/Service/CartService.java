package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

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

import jakarta.transaction.Transactional;

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


@Transactional
	public List<CartItemEntity> getbyUserId() {
		Long id= userService.getCurrentUserId();
		
		
		 CartEntity cart = cartRepo.findByUserId(id);
		        
		 for (CartItemEntity cartItem : cart.getCartItems()) {
		
		        ProductEntity product = cartItem.getProduct();
		        
		        String imageBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImageData());
	            product.setImageBase64(imageBase64); 
		       
		        
		    }
		 
		 return cart.getCartItems();
		
	}



	public void updateQuantity(Long productId, Integer quantity) {
		
		 CartItemEntity cartItem = cartItemRepo.findByProductId(productId);
		          
		    cartItem.setQuantity(quantity);
		    cartItemRepo.save(cartItem);
		
	}
}

