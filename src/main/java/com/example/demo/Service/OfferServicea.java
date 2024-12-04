package com.example.demo.Service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Service.utilty.Commonutil;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.repositry.OfferRepository;

import jakarta.transaction.Transactional;

@Service
public class OfferServicea {
	
	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	private Productservices productservice;
	
	@Autowired
	Commonutil commonutil;

	public List<OfferEntity> getAllOffers() {
				
			
	List<OfferEntity> offers= offerRepository.findAll();
	
	
	 
    for (OfferEntity offer : offers) {
      
        offer.setActive(offer.getOfferEndDate().isAfter(LocalDate.now()));

       
        ProductEntity product = offer.getProduct();

       
       Object productEntity = commonutil.convertImageDataToBase64(product);

        

       
    }
    
    return offers;
	
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

public void addOffer(Long productId, double discountPercentage, LocalDate endDate) {
    
    ProductEntity product = productservice.GetById(productId);
    

  
  
    double discountPrice = product.getPrice() - (product.getPrice() * (discountPercentage / 100.0));

    // Validate offer end date
    if (endDate.isBefore(LocalDate.now())) {
        throw new IllegalArgumentException("End date must be today or later.");
    }

    
    OfferEntity offer = new OfferEntity();
    offer.setProduct(product);
    offer.setDiscountPercentage(discountPercentage);
    offer.setOfferPrice(discountPrice);
    offer.setOfferEndDate(endDate);

    offerRepository.save(offer);
}
public void deleteOffer(Long id) {
	offerRepository.deleteById(id);
	
}



	
}
    

