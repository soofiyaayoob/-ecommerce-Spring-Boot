package com.example.demo.Service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.OfferEntity;
import com.example.demo.repositry.OfferRepository;

import jakarta.transaction.Transactional;

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
@Transactional
	public List<OfferEntity> get4Greatoffers() {
	
		List<OfferEntity> offers= offerRepository.findTop4ByOfferEndDateAfterOrderByDiscountPercentageDesc(LocalDate.now());
		
		offers.forEach(offer -> {
	        // Assuming the product's image is stored in bytes in the database
	        byte[] imageBytes = offer.getProduct().getImageData();
	        System.out.println("Image size: " + imageBytes.length);

	        if (imageBytes != null) { // Check if the image bytes are not null
	            String base64Image ="data:image/png;base64,"+ Base64.getEncoder().encodeToString(imageBytes);
	            offer.getProduct().setImageBase64(base64Image); // assuming PNG format
	        }
	    });
		return offers;
	}
    
}
