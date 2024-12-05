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
@Table(name = "offers")
@Check(constraints = "end_date > start_date")

public class OfferEntity {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToOne
	    @JoinColumn(name = "product_id", nullable = false)
	    private ProductEntity product;

	    @Column(nullable = false)
	    private Double percentage;
	    
	    @Column(nullable = false)
	    private Double offerPrice;

	    @Column(nullable = false)
	    private LocalDate startDate;

	  

	    @Column(nullable = false)
	    private LocalDate endDate;
	    
	    private transient boolean isActive=false;
	    
	     
	    @PrePersist
	    private void setDefaultOfferStartDate() {
	        
	        startDate = LocalDate.now();
	        
	    }

	    
	    
	    
	    


}
