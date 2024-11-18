package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

public boolean addProductToWishlist(Long id, String username) {

    UserEntity user = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    
    ProductEntity product = productRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

   
    boolean productExists = user.getWishlists().stream()
            .anyMatch(wishlist -> wishlist.getProduct().getId().equals(product.getId()));

    if (productExists) {
       
        return false;
    }

  
    WishlistEntity wishlistEntity = new WishlistEntity();
    wishlistEntity.setUser(user);
    wishlistEntity.setProduct(product);

    wishlistRepo.save(wishlistEntity);
    return true; 
}




}
