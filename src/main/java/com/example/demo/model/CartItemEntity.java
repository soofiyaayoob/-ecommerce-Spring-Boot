package com.example.demo.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItemEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;  // Each item belongs to a cart

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product; 
	
    
    private BigDecimal priceAfterDiscount;

    private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public BigDecimal getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public void setPriceAfterDiscount(BigDecimal priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CartItemEntity(Long id, CartEntity cart, ProductEntity product, BigDecimal priceAfterDiscount,
			Integer quantity) {
		super();
		this.id = id;
		this.cart = cart;
		this.product = product;
		this.priceAfterDiscount = priceAfterDiscount;
		this.quantity = quantity;
	}

	public CartItemEntity() {
		
	}
    
    
}
