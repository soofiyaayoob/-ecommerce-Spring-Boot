package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishList")
public class WishlistEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	 @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private UserEntity user; 
	    
	 @ManyToOne()
	    @JoinColumn(name = "product_id", nullable = false)
	    private ProductEntity product;

	    

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public UserEntity getUser() {
			return user;
		}

		public void setUser(UserEntity user) {
			this.user = user;
		}

		public ProductEntity getProduct() {
			return product;
		}

		public void setProduct(ProductEntity product) {
			this.product = product;
		}

	

		public WishlistEntity(Long id, UserEntity user, ProductEntity product) {
			super();
			this.id = id;
			this.user = user;
			this.product = product;
			
		}

		public WishlistEntity() {
			
		}
	    
	    

}
