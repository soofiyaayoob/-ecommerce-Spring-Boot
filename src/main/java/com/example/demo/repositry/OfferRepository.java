package com.example.demo.repositry;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OfferEntity;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>{


	List<OfferEntity> findTop4ByEndDateAfterOrderByPercentageDesc(LocalDate now);

	List<OfferEntity> findByProductUserId(Long currentUserId);
	
}
