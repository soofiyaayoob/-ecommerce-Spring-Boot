package com.example.demo.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Aspect.EmailService;
import com.example.demo.Aspect.OtpService;
import com.example.demo.CustomHandler.SucessHandler;
import com.example.demo.Service.OfferServicea;
import com.example.demo.Service.Productservices;
import com.example.demo.Service.UserService;
import com.example.demo.Service.wishlistService;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller
public class Homecontroller {
	
	private static final Logger logger=LoggerFactory.getLogger(Homecontroller.class);
	
	@Autowired
	OfferServicea offerService;
	
	@Autowired
	Productservices productservices;
	
	@Autowired
	wishlistService wishlistService;
	
	
	 @GetMapping("/")
	    public String home(Model model) {
		 List<OfferEntity>offers=offerService.get4Greatoffers();
		
		List<ProductEntity>latestproducts=productservices.LatestProduct();
		List<ProductEntity>Allproducts=productservices.getAllProducts();
		List<CategoryEntity>Allcategory=productservices.getAllCategories();
		
		   model.addAttribute("greatOffers", offers);
		   model.addAttribute("latestProducts",latestproducts);
		   model.addAttribute("products",Allproducts);
		   model.addAttribute("categories",Allcategory);
		   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null) {
	            logger.info("Principal: {}", authentication.getPrincipal());
	            System.out.println("authentication is not null");
	        } else {
	            logger.info("No authentication context found.");
	          System.out.println("it is null");
	            
	            
	        }
	    
		    return "home";
}
	  @GetMapping("/categories/{id}")
	    public String getProductsByCategory(@PathVariable Long id, Model model) {
	     
	        List<ProductEntity> products = productservices.getProductsByCategory(id);

	        model.addAttribute("products", products);

	        return "CategoryProduct"; 
	    }
	  @PostMapping("/Addwishlist/{id}")
	  
	  public String addToWishlist(@PathVariable Long id,HttpSession session) {
		  try {
			  System.out.println("coming");
		        wishlistService.addProductToWishlist(id);
		        session.setAttribute("message", "Product successfully added to wishlist");
		    } catch (Exception e) {
		 
		        session.setAttribute("error", "An error occurred while adding product to wishlist");
		    }
		  return "home"; 
	  }
	  @PostMapping("/AddCart/{id}")
	  public void addTocart(@PathVariable Long id,HttpServletResponse response) {
	      
	      System.out.println("Product added to cart: " + id);
	      response.setStatus(HttpServletResponse.SC_NO_CONTENT); 
	     
	  }

	
}	



	
	
	

