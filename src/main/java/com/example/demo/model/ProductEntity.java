package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductEntity {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String name;

	    @Column(length = 500)
	    private String description;

	    @Column
	    private String image;

	    @Column(nullable = false)
	    private Double price;

	    // One-to-many relationship with ProductOffer
	    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<OfferEntity> offers;

	    // One-to-many relationship with Size
	    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<SizeEntity> sizes;
	    
	    @ManyToOne
	    @JoinColumn(name = "category_id", nullable = false)  // Foreign key in product table
	    private CategoryEntity category;


	    // Getters and setters
}
