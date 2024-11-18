package com.example.demo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.model.CartEntity;
@Repository
public interface cartRepo extends JpaRepository<CartEntity, Long>{

	CartEntity findByUserId(Long userId);

}