package com.example.demo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.AddressEntity;
@Repository
public interface AddressRepo extends JpaRepository<AddressEntity, Long>{

	List<AddressEntity> findByUserId(Long userId);
	

}
