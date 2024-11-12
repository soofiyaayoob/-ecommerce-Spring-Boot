package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishListItem")
public class wishlistItemEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id", nullable = false)
    private WishlistEntity wishlist;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WishlistEntity getWishlist() {
		return wishlist;
	}

	public void setWishlist(WishlistEntity wishlist) {
		this.wishlist = wishlist;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public wishlistItemEntity(Long id, WishlistEntity wishlist, ProductEntity product, Integer quantity) {
		super();
		this.id = id;
		this.wishlist = wishlist;
		this.product = product;
		this.quantity = quantity;
	}

	public wishlistItemEntity() {
		super();
	}   
    
    

}
