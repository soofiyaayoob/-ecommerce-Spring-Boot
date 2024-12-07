package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderEntity;
@Repository
public interface OrderRepo extends JpaRepository<OrderEntity , Long>{

	List<OrderEntity> findByUserId(Long id);

}
