package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.auditing.CurrentDateTimeProvider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cart")
public class CartEntity {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToOne
	    @JoinColumn(name = "user_id", nullable = false,unique = true)
	    private UserEntity user; 
	    
	    
	    @Column(nullable = false)
	    private LocalDateTime createdAt;

	    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<CartItemEntity> cartItems;

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

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public List<CartItemEntity> getCartItems() {
			return cartItems;
		}

		public void setCartItems(List<CartItemEntity> cartItems) {
			this.cartItems = cartItems;
		}

		public CartEntity(Long id, UserEntity user, LocalDateTime createdAt, List<CartItemEntity> cartItems) {
			super();
			this.id = id;
			this.user = user;
			this.createdAt = createdAt;
			this.cartItems = cartItems;
		}

		public CartEntity() {
			super();
		}

		
	    
	

}
