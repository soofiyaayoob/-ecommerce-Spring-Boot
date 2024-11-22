package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressEntity {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String state;

	    @Column(nullable = false)
	    private String city;

	    @Column(nullable = false)
	    private String pincode;

	    
	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false) 
	    private UserEntity user;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPincode() {
			return pincode;
		}

		public void setPincode(String pincode) {
			this.pincode = pincode;
		}

		public UserEntity getUser() {
			return user;
		}

		public void setUser(UserEntity user) {
			this.user = user;
		}

		public AddressEntity(Long id, String state, String city, String pincode, UserEntity user) {
			super();
			this.id = id;
			this.state = state;
			this.city = city;
			this.pincode = pincode;
			this.user = user;
		}

		public AddressEntity() {
			
		}



}
