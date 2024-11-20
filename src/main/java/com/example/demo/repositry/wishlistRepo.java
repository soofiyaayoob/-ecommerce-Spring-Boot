package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.WishlistEntity;

@Repository
public interface wishlistRepo extends JpaRepository<WishlistEntity, Long>{

	List<WishlistEntity> findByUserId(Long id);

}
