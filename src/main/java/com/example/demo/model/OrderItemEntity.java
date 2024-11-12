package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
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
	    @JoinColumn(name = "Order_ref", nullable = false)
	    private OrderEntity order; 

	    @ManyToOne
	    @JoinColumn(name = "product_id", nullable = false)
	    private ProductEntity product;  

	    private BigDecimal priceAtOrder;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public OrderEntity getOrder() {
			return order;
		}

		public void setOrder(OrderEntity order) {
			this.order = order;
		}

		public ProductEntity getProduct() {
			return product;
		}

		public void setProduct(ProductEntity product) {
			this.product = product;
		}

		public BigDecimal getPriceAtOrder() {
			return priceAtOrder;
		}

		public void setPriceAtOrder(BigDecimal priceAtOrder) {
			this.priceAtOrder = priceAtOrder;
		}

		public OrderItemEntity(Long id, OrderEntity order, ProductEntity product, BigDecimal priceAtOrder) {
			
			this.id = id;
			this.order = order;
			this.product = product;
			this.priceAtOrder = priceAtOrder;
		}

		public OrderItemEntity() {
			
		}
	    
	    
}
