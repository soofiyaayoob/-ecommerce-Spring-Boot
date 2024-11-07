package com.example.demo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ProductEntity;

public interface productRepo extends JpaRepository<ProductEntity, Long>{

}
