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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") 
public class UserEntity {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column
	    private String fullName;

	    @Column( unique = true)
	    private String username;

	    @Column
	    private String mobileNo;

	    @Column
	    private String gender;
	    
	    @Column
	    private String password;
	    
	    @Column
	    private String email;
	    
	    @Column
	    private boolean isActive=true;
	    
	    @Column
	    private String token;
	    
	    @Enumerated(EnumType.STRING)
	    private Role role = Role.USER;
	    
	    
	    
	    public enum Role {
	        USER,    // Default role for regular users
	        ADMIN,   // Role for admin users with elevated permissions
	        RESTAURANT
	    }
	    
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<AddressEntity> addresses=new ArrayList<>();
	    
	    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	    private CartEntity cart;
	    
	    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	    private List<OrderEntity> orderProducts;
	    
	    
	    @OneToMany(mappedBy = "user",  orphanRemoval = true)
	    private List<WishlistEntity> wishlists;

	    
 
}
