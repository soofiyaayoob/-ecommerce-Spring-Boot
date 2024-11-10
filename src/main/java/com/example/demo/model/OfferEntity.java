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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_offers")
@Check(constraints = "offer_end_date > offer_start_date")

public class OfferEntity {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long offerId;

	    @OneToOne
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
	        
	        offerStartDate = LocalDate.now();
	        
	    }


		public Long getOfferId() {
			return offerId;
		}


		public void setOfferId(Long offerId) {
			this.offerId = offerId;
		}


		public ProductEntity getProduct() {
			return product;
		}


		public void setProduct(ProductEntity product) {
			this.product = product;
		}


		public Double getDiscountPercentage() {
			return discountPercentage;
		}


		public void setDiscountPercentage(Double discountPercentage) {
			this.discountPercentage = discountPercentage;
		}


		public Double getOfferPrice() {
			return OfferPrice;
		}


		public void setOfferPrice(Double offerPrice) {
			OfferPrice = offerPrice;
		}


		public LocalDate getOfferStartDate() {
			return offerStartDate;
		}


		public void setOfferStartDate(LocalDate offerStartDate) {
			this.offerStartDate = offerStartDate;
		}


		public LocalDate getOfferEndDate() {
			return offerEndDate;
		}


		public void setOfferEndDate(LocalDate offerEndDate) {
			this.offerEndDate = offerEndDate;
		}


		public OfferEntity(Long offerId, ProductEntity product, Double discountPercentage, Double offerPrice,
				LocalDate offerStartDate, LocalDate offerEndDate) {
			super();
			this.offerId = offerId;
			this.product = product;
			this.discountPercentage = discountPercentage;
			OfferPrice = offerPrice;
			this.offerStartDate = offerStartDate;
			this.offerEndDate = offerEndDate;
		}


		public OfferEntity() {
			
		}
	    
	    
	    
	    


}
