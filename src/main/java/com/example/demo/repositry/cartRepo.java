package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.model.CartEntity;
@Repository
public interface cartRepo extends JpaRepository<CartEntity, Long>{

	CartEntity findByUserId(Long userId);

	void deleteByUserId(Long currentUserId);

}
