package com.example.demo.model;

import java.time.LocalDateTime;

import org.apache.catalina.User;
import org.springframework.data.auditing.CurrentDateTimeProvider;

import jakarta.persistence.Entity;
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
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private UserEntity user;
	
	private CurrentDateTimeProvider orderAt;
	
	private LocalDateTime orderedAt;
	
	 @ManyToOne
     @JoinColumn(name = "address_id")
	 private AddressEntity shippingAddress; 
	 
	 @PrePersist
	    protected void onCreate() {
	        this.orderedAt = LocalDateTime.now();
	    }

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

	public CurrentDateTimeProvider getOrderAt() {
		return orderAt;
	}

	public void setOrderAt(CurrentDateTimeProvider orderAt) {
		this.orderAt = orderAt;
	}

	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}

	public AddressEntity getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressEntity shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public OrderEntity(Long id, UserEntity user, CurrentDateTimeProvider orderAt, LocalDateTime orderedAt,
			AddressEntity shippingAddress) {
		super();
		this.id = id;
		this.user = user;
		this.orderAt = orderAt;
		this.orderedAt = orderedAt;
		this.shippingAddress = shippingAddress;
	}

	public OrderEntity() {
		
	}
	 
	 

}
