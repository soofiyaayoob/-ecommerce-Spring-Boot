package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sizes")
public class SizeEntity {
	


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String sizeName;  // e.g., Small, Medium, Large

	    @ManyToOne
	    @JoinColumn(name = "product_id", nullable = false)
	    private ProductEntity product;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSizeName() {
			return sizeName;
		}

		public void setSizeName(String sizeName) {
			this.sizeName = sizeName;
		}

		public ProductEntity getProduct() {
			return product;
		}

		public void setProduct(ProductEntity product) {
			this.product = product;
		}

		public SizeEntity(Long id, String sizeName, ProductEntity product) {
			super();
			this.id = id;
			this.sizeName = sizeName;
			this.product = product;
		}

		public SizeEntity() {
			
		}

	  
	}



