package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ProductEntity;

public interface productRepo extends JpaRepository<ProductEntity, Long>{
	List<ProductEntity> findTop5ByOrderByIdDesc();

	List<ProductEntity> findByCategoryId(Long id);
}
