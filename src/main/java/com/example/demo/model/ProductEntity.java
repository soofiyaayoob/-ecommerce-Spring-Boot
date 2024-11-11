package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

	    @Column@Lob
	    private byte[] imageDate;
	    
	    @Column
	    private String imageName;
	    
	  
	    private transient String imageBase64;

	    @Column(nullable = false)
	    private Double price;

	   
	    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	    private OfferEntity offer;

	    // One-to-many relationship with Size
	    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<SizeEntity> sizes;
	    
	    @ManyToOne
	    @JoinColumn(name = "category_id", nullable = false)  // Foreign key in product table
	    private CategoryEntity category;

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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public byte[] getImageDate() {
			return imageDate;
		}

		public void setImageDate(byte[] imageDate) {
			this.imageDate = imageDate;
		}

		public String getImageName() {
			return imageName;
		}

		public void setImageName(String imageName) {
			this.imageName = imageName;
		}

		public String getImageBase64() {
			return imageBase64;
		}

		public void setImageBase64(String imageBase64) {
			this.imageBase64 = imageBase64;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public OfferEntity getOffer() {
			return offer;
		}

		public void setOffer(OfferEntity offer) {
			this.offer = offer;
		}

		public List<SizeEntity> getSizes() {
			return sizes;
		}

		public void setSizes(List<SizeEntity> sizes) {
			this.sizes = sizes;
		}

		public CategoryEntity getCategory() {
			return category;
		}

		public void setCategory(CategoryEntity category) {
			this.category = category;
		}

		public ProductEntity(Long id, String name, String description, byte[] imageDate, String imageName,
				String imageBase64, Double price, OfferEntity offer, List<SizeEntity> sizes, CategoryEntity category) {
			
			this.id = id;
			this.name = name;
			this.description = description;
			this.imageDate = imageDate;
			this.imageName = imageName;
			this.imageBase64 = imageBase64;
			this.price = price;
			this.offer = offer;
			this.sizes = sizes;
			this.category = category;
		}

		public ProductEntity() {
			
		}




	    // Getters and setters
	    
}
