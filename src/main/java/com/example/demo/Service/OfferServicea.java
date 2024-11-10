package com.example.demo.Service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OfferEntity;
import com.example.demo.repositry.OfferRepository;

@Service
public class OfferServicea {
	
	@Autowired
	OfferRepository offerRepository;

	public void addNewOffer(OfferEntity offerEntity) {
		
			offerRepository.save(offerEntity);
	
		
	}

	public List<OfferEntity> getAllOffers() {
		
		return offerRepository.findAll();
	}

}
