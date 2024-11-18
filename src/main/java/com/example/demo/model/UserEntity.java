package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users") 
public class UserEntity {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String fullName;

	    @Column(nullable = false, unique = true)
	    private String username;

	    @Column(nullable = false, unique = true)
	    private String mobileNo;

	    @Column(nullable = false)
	    private String gender;
	    
	    @Column(nullable = false)
	    private String password;
	    
	    @Column(nullable = false)
	    private String email;
	    
	    @Column
	    private boolean isActive=true;
	    
	    @Enumerated(EnumType.STRING)
	    private Role role = Role.USER;
	    
	    public enum Role {
	        USER,    // Default role for regular users
	        ADMIN,   // Role for admin users with elevated permissions
	        SUPERADMIN  // Highest role for users with all permissions
	    }
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<AddressEntity> addresses=new ArrayList<>();
	    
	    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	    private CartEntity cart;
	    
	    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	    private List<OrderEntity> orderProducts;
	    
	    
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<WishlistEntity> wishlists;


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getFullName() {
			return fullName;
		}


		public void setFullName(String fullName) {
			this.fullName = fullName;
		}


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public String getMobileNo() {
			return mobileNo;
		}


		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}


		public String getGender() {
			return gender;
		}


		public void setGender(String gender) {
			this.gender = gender;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public boolean isActive() {
			return isActive;
		}


		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}


		public Role getRole() {
			return role;
		}


		public void setRole(Role role) {
			this.role = role;
		}


		public List<AddressEntity> getAddresses() {
			return addresses;
		}


		public void setAddresses(List<AddressEntity> addresses) {
			this.addresses = addresses;
		}


		public CartEntity getCart() {
			return cart;
		}


		public void setCart(CartEntity cart) {
			this.cart = cart;
		}


		public List<OrderEntity> getOrderProducts() {
			return orderProducts;
		}


		public void setOrderProducts(List<OrderEntity> orderProducts) {
			this.orderProducts = orderProducts;
		}


		public List<WishlistEntity> getWishlists() {
			return wishlists;
		}


		public void setWishlists(List<WishlistEntity> wishlists) {
			this.wishlists = wishlists;
		}


		public UserEntity(Long id, String fullName, String username, String mobileNo, String gender, String password,
				String email, boolean isActive, Role role, List<AddressEntity> addresses, CartEntity cart,
				List<OrderEntity> orderProducts, List<WishlistEntity> wishlists) {
			super();
			this.id = id;
			this.fullName = fullName;
			this.username = username;
			this.mobileNo = mobileNo;
			this.gender = gender;
			this.password = password;
			this.email = email;
			this.isActive = isActive;
			this.role = role;
			this.addresses = addresses;
			this.cart = cart;
			this.orderProducts = orderProducts;
			this.wishlists = wishlists;
		}


		public UserEntity() {
			super();
		}

	
	    
 
}
