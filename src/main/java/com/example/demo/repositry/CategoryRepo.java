package com.example.demo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CategoryEntity;
@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Long>{

}
