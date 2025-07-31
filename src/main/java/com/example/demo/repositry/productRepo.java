package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.CategoryEntity;
import com.example.demo.model.ProductEntity;

public interface productRepo extends JpaRepository<ProductEntity, Long>{
	List<ProductEntity> findTop6ByOrderByIdDesc();

	List<ProductEntity> findByCategoryId(Long id);

	List<ProductEntity> findByNameContainingIgnoreCase(String query);

	  List<ProductEntity> findByUserId(Long currentUserId);

	  @Query("SELECT p FROM ProductEntity p " +
	           "JOIN p.user u " +
	           "JOIN u.addresses a " +
	           "WHERE LOWER(a.city) = LOWER(:location) OR LOWER(a.state) = LOWER(:location)")
	List<ProductEntity> findProductsByLocation(@Param("location") String location);

//	  "WHERE LOWER(a.city) LIKE LOWER(CONCAT('%', :location, '%')) " +
//      "OR LOWER(a.state) LIKE LOWER(CONCAT('%', :location, '%'))")
	
}
