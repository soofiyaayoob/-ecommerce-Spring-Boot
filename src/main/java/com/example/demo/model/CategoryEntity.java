package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="category")
public class CategoryEntity {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	   
	   
	   @Column(nullable = false)
	    private String name;

	    @OneToMany(mappedBy = "category")  // "category" is the field in Product
	    private List<ProductEntity> products; // Optional, useful for retrieving products by category

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<ProductEntity> getProducts() {
			return products;
		}

		public void setProducts(List<ProductEntity> products) {
			this.products = products;
		}

		public CategoryEntity(Long id, String name, List<ProductEntity> products) {
			super();
			this.id = id;
			this.name = name;
			this.products = products;
		}

		public CategoryEntity() {
			
		}
		
		

	    // Constructors, getters, and setters
	    
	    

}
