package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.WishlistEntity;
import com.example.demo.repositry.UserRepo;
import com.example.demo.repositry.productRepo;
import com.example.demo.repositry.wishlistRepo;

@Service
public class wishlistService {
@Autowired
wishlistRepo wishlistRepo;


@Autowired
UserRepo userRepo;

@Autowired
productRepo productRepo;

public void addProductToWishlist(Long id) {
	
    // Fetch user and product
	String username = SecurityContextHolder.getContext().getAuthentication().getName();

    // Now you can use the `username` to find the user
    UserEntity user = userRepo.findByUsername(username);
                                    

    ProductEntity product = productRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    
   // int finalQuantity = (quantity != null) ? quantity : 1;
   
    WishlistEntity wishlistEntity = new WishlistEntity();
    wishlistEntity.setUser(user);
    wishlistEntity.setProduct(product);
  
    
    
   wishlistRepo.save(wishlistEntity);

}




}
