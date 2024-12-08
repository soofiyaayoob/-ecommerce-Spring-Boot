package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="category")
public class CategoryEntity {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	   
	   
	   @Column(nullable = false)
	    private String name;
	   
	   
	   @Column(nullable = false)
	   private String imageName;
	   
	    @Column@Lob
	    private byte[] imageData;
	    
	   
	   private transient String imageBase64;

	    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)  
	    private List<ProductEntity> products; 

	    
	  
	    public int getProductCount() {
	        return products != null ? products.size() : 0;
	    }

}
