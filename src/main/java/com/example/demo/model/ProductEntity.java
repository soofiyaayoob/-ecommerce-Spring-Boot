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
	    private byte[] imageDate;
	    
	    @Column
	    private String imageName;

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

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public List<OfferEntity> getOffers() {
			return offers;
		}

		public void setOffers(List<OfferEntity> offers) {
			this.offers = offers;
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

		public ProductEntity(Long id, String name, String description, byte[] imageDate, String imageName, Double price,
				List<OfferEntity> offers, List<SizeEntity> sizes, CategoryEntity category) {


			this.id = id;
			this.name = name;
			this.description = description;
			this.imageDate = imageDate;
			this.imageName = imageName;
			this.price = price;
			this.offers = offers;
			this.sizes = sizes;
			this.category = category;
		}

		public ProductEntity() {
			
		}

	


	    // Getters and setters
	    
}
