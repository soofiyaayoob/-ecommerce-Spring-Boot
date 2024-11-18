package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	   
	   
	   @Column(nullable = false)
	   private String imageName;
	   
	    @Column@Lob
	    private byte[] imageData;
	    
	   
	   private transient String imageBase64;

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

		public String getImageName() {
			return imageName;
		}

		public void setImageName(String imageName) {
			this.imageName = imageName;
		}

		public List<ProductEntity> getProducts() {
			return products;
		}

		public void setProducts(List<ProductEntity> products) {
			this.products = products;
		}

		

		
		public CategoryEntity(Long id, String name, String imageName, byte[] imageData, String imageBase64,
				List<ProductEntity> products) {
			super();
			this.id = id;
			this.name = name;
			this.imageName = imageName;
			this.imageData = imageData;
			this.imageBase64 = imageBase64;
			this.products = products;
		}

		public CategoryEntity() {
			
		}

		public String getImageBase64() {
			return imageBase64;
		}

		public void setImageBase64(String imageBase64) {
			this.imageBase64 = imageBase64;
		}

		public byte[] getImageData() {
			return imageData;
		}

		public void setImageData(byte[] imageData) {
			this.imageData = imageData;
		}

		

	    
	    

}
