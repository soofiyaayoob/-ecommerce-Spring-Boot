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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	    
	    @OneToMany(cascade = CascadeType.ALL,mappedBy = "shippingAddress") 
	    private List<OrderEntity>order;

	


}
