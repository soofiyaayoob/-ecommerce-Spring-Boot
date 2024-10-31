package com.example.demo.model;

import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
	    private String Email;
	    
	    @Enumerated(EnumType.STRING)
	    private Role role = Role.USER;
	    
	    public enum Role {
	        USER,    // Default role for regular users
	        ADMIN,   // Role for admin users with elevated permissions
	        SUPERADMIN  // Highest role for users with all permissions
	    }
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<AddressEntity> addresses;
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
			return Email;
		}
		public void setEmail(String email) {
			Email = email;
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
		public UserEntity(Long id, String fullName, String username, String mobileNo, String gender, String password,
				String email, Role role, List<AddressEntity> addresses) {
			super();
			this.id = id;
			this.fullName = fullName;
			this.username = username;
			this.mobileNo = mobileNo;
			this.gender = gender;
			this.password = password;
			Email = email;
			this.role = role;
			this.addresses = addresses;
		}
		public UserEntity() {
			
		}
		
	    
	    
	    
	    
	    
}
