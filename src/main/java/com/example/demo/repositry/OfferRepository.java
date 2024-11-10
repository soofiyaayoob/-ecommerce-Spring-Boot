package com.example.demo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OfferEntity;
import com.example.demo.model.SizeEntity;
@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>{

}
