package com.example.demo.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItemEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	

    @ManyToOne(cascade = CascadeType.ALL)  
    @JoinColumn(name = "cart_id")
    private CartEntity cart; 

    @ManyToOne(cascade = CascadeType.ALL)  
    @JoinColumn(name = "product_id")
    private ProductEntity product; 
	
    
   

    private Integer quantity=1;

    
    
}
