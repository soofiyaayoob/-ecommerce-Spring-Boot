package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "products")
public class ProductEntity {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String name;

	    @Column(length = 500)
	    private String description;

	    @Lob
	    @Column
        private byte[] imageData;
	    
	    @Column
	    private String imageName;
	    
	  
	    private transient String imageBase64;

	    @Column(nullable = false)
	    private Double price;

	   
	    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	    private OfferEntity offer;

	    @ManyToOne
	    @JoinColumn(name = "user_id",nullable = false)  // Foreign key to link product to a restaurant owner (user)
	    private UserEntity user; 
	  
	    
	    @ManyToOne
	    @JoinColumn(name = "category_id", nullable = false)  // Foreign key in product table
	    private CategoryEntity category;
	    
	    @OneToMany(mappedBy = "product")
	    private List<OrderItemEntity> orderProducts;

	    @OneToMany(mappedBy = "product")
	    private List<WishlistEntity> wishlistItem;

	  
	    


	    
	    
}
