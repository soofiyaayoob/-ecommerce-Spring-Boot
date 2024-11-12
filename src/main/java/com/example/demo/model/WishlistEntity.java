package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishList")
public class WishlistEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL)
	    private List<wishlistItemEntity> wishlistitem;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public List<wishlistItemEntity> getWishlistitem() {
			return wishlistitem;
		}

		public void setWishlistitem(List<wishlistItemEntity> wishlistitem) {
			this.wishlistitem = wishlistitem;
		}

		public WishlistEntity(Long id, List<wishlistItemEntity> wishlistitem) {
			super();
			this.id = id;
			this.wishlistitem = wishlistitem;
		}

		public WishlistEntity() {
			
		}  

	    
}
