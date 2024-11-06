package com.example.demo.model;

import java.time.LocalDate;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_offers")
@Check(constraints = "offer_end_date > offer_start_date")

public class OfferEntity {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long offerId;

	    @ManyToOne
	    @JoinColumn(name = "product_id", nullable = false)
	    private ProductEntity product;

	    @Column(nullable = false)
	    private Double discountPercentage;
	    
	    @Column(nullable = false)
	    private Double OfferPrice;

	    @Column(nullable = false)
	    private LocalDate offerStartDate;

	  

	    @Column(nullable = false)
	    private LocalDate offerEndDate;
	    
	    
	    @PrePersist
	    private void setDefaultOfferStartDate() {
	        // Set the current date as the default for offerStartDate if not already set
	        offerStartDate = LocalDate.now();
	        
	    }
	    
	    
	    


}
