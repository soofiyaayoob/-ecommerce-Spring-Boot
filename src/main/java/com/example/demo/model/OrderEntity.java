package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.EnumSet;


import org.apache.catalina.User;
import org.springframework.data.auditing.CurrentDateTimeProvider;

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
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private UserEntity user;
	
	private CurrentDateTimeProvider orderAt;
	
	private LocalDateTime orderedAt;

@Enumerated(EnumType.STRING)
	private OrderStatus status=OrderStatus.PENDING;

// Enum for Order Status
public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    CANCELED,
    DELIVERED,
    RETURNED;
	private static final EnumMap<OrderStatus, EnumSet<OrderStatus>> validTransitions = new EnumMap<>(OrderStatus.class);

    static {
        validTransitions.put(PENDING, EnumSet.of(PROCESSING, CANCELED));
        validTransitions.put(PROCESSING, EnumSet.of(SHIPPED, CANCELED));
        validTransitions.put(SHIPPED, EnumSet.of(DELIVERED, CANCELED));
        validTransitions.put(DELIVERED, EnumSet.of(RETURNED));
        validTransitions.put(RETURNED, EnumSet.noneOf(OrderStatus.class)); // No transitions allowed from RETURNED
        validTransitions.put(CANCELED, EnumSet.noneOf(OrderStatus.class)); // No transitions allowed from CANCELED
    }

    public boolean canTransitionTo(OrderStatus targetStatus) {
        return validTransitions.get(this).contains(targetStatus);
    }

    
}
	
	 @ManyToOne
     @JoinColumn(name = "address_id")
	 private AddressEntity shippingAddress; 
	 
	 private String paymentType;
	 
	
	 @PrePersist
	    protected void onCreate() {
	        this.orderedAt = LocalDateTime.now();
	    }
	 

	    public boolean updateStatus(OrderStatus newStatus) {
	        if (this.status.canTransitionTo(newStatus)) {
	            this.status = newStatus;
	            return true;
	        } else {
	            System.out.println("Invalid status transition: " + this.status + " -> " + newStatus);
	            return false;
	        }
	    }




}
