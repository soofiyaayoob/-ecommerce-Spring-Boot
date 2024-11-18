package com.example.demo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CartEntity;
import com.example.demo.model.CartItemEntity;
import com.example.demo.model.ProductEntity;
@Repository
public interface CartItemRepo extends JpaRepository<CartItemEntity, Long>{

	CartItemEntity findByCartAndProduct(CartEntity cart, ProductEntity product);

}
