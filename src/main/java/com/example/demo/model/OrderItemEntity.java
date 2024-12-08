package com.example.demo.model;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.EnumSet;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "OrderItem")
public class OrderItemEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "Order_id", nullable = false)
	    private OrderEntity order; 

	    @ManyToOne
	    @JoinColumn(name = "product_id", nullable = false)
	    private ProductEntity product;  
	    
	    private Integer quantity;
	    
	    private double price;


@Enumerated(EnumType.STRING)
	private OrderStatus status=OrderStatus.PENDING;

// Enum for Order Status
public enum OrderStatus {
    PENDING,
    SHIPPED,
    CANCELED,
    DELIVERED;
   
	private static final EnumMap<OrderStatus, EnumSet<OrderStatus>> validTransitions = new EnumMap<>(OrderStatus.class);

    static {
        validTransitions.put(PENDING, EnumSet.of(SHIPPED, CANCELED));
        validTransitions.put(SHIPPED, EnumSet.of(DELIVERED, CANCELED));
        validTransitions.put(DELIVERED, EnumSet.noneOf(OrderStatus.class));
     
        validTransitions.put(CANCELED, EnumSet.noneOf(OrderStatus.class)); 
    }

    public boolean canTransitionTo(OrderStatus targetStatus) {
        return validTransitions.get(this).contains(targetStatus);
    }

    
}
}


