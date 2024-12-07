package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CategoryEntity;
import com.example.demo.model.ProductEntity;

public interface productRepo extends JpaRepository<ProductEntity, Long>{
	List<ProductEntity> findTop5ByOrderByIdDesc();

	List<ProductEntity> findByCategoryId(Long id);

	List<ProductEntity> findByNameContainingIgnoreCase(String query);
	
	
}
