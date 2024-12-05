package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.auditing.CurrentDateTimeProvider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Table(name = "Orders")
public class OrderEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String orderId;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private UserEntity user;
	
	
	private LocalDateTime orderedAt;

	 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	    private List<OrderItemEntity> items =new ArrayList<>();
	
	 @ManyToOne
     @JoinColumn(name = "address_id")
	 private AddressEntity shippingAddress; 
	 
	 private String paymentType;
	 
	
	 @PrePersist
	    protected void onCreate() {
	        this.orderedAt = LocalDateTime.now();
	    }
	 

	   




}
