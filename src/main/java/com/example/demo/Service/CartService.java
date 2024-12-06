package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Service.utilty.Commonutil;
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
	
	@Autowired
	Commonutil commonutil;
	
	
	
	public boolean addProductToCart(Long productId) throws AttributeNotFoundException {
	    Long userId = commonutil.getCurrentUserId(); 
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
	public List<CartItemEntity> getbyUserId() throws Exception {
		Long id= commonutil.getCurrentUserId();
		
		
		 CartEntity cart = cartRepo.findByUserId(id);
		 if (cart == null) {
	            throw new Exception("Cart not found for user with ID: " + id);
	        }
		        
		 for (CartItemEntity cartItem : cart.getCartItems()) {
		
		        ProductEntity product = cartItem.getProduct();
		        
		        String imageBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImageData());
	            product.setImageBase64(imageBase64); 
		       
		        
		    }
		 
		 return cart.getCartItems();
		
	}


@Transactional
	public void updateQuantity(Long productId, Integer quantity) {
		
		 CartItemEntity cartItem = cartItemRepo.findByProductId(productId);
		          
		    cartItem.setQuantity(quantity);
		    cartItemRepo.save(cartItem);
		
	}


	public double calculateTotalprice(List<CartItemEntity> cart) {
		
		return cart.stream()
			    .mapToDouble(item -> {
			        ProductEntity product = item.getProduct();
			        double price = (product.getOffer() != null)
			                ? product.getOffer().getOfferPrice()
			                : product.getPrice();
			        return item.getQuantity() * price;
			    })
			    .sum();
	}


	public double calculateExactPrice(List<CartItemEntity> cart) {
		
		return cart.stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();

	}

@Transactional
	public void deleteProductById(Long id) {
	long userid= commonutil.getCurrentUserId();
	cartItemRepo.deleteCartItemByUserIdAndProductId(userid, id);
		
	}


public void clearCart() {
	
	//cartItemRepo.deleteBycartId();
	cartRepo.deleteByUserId(commonutil.getCurrentUserId());
	
}


}

