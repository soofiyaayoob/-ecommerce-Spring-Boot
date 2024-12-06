package com.example.demo.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CartEntity;
import com.example.demo.model.CartItemEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;

import jakarta.transaction.Transactional;
@Repository
public interface CartItemRepo extends JpaRepository<CartItemEntity, Long>{

	CartItemEntity findByCartAndProduct(CartEntity cart, ProductEntity product);

	CartItemEntity findByProductId(Long productId);


	@Modifying
    @Transactional
    @Query("DELETE FROM CartItemEntity ci WHERE ci.cart.user.id = :userId AND ci.product.id = :productId")
    void deleteCartItemByUserIdAndProductId(@Param("userId") long userId, @Param("productId") Long productId);

//	void deleteByUserId(Long currentUserId);

}
