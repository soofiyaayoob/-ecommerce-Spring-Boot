package com.example.demo.controller;

import java.security.Principal;
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
import com.example.demo.Service.CartService;
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
	
	@Autowired
	CartService cartService;
	
	
	 @GetMapping("/")
	    public String home(Model model,HttpSession session) {
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
//	        
//	        String message = (String) session.getAttribute("message");
//	        String error = (String) session.getAttribute("error");
//
//	        // Add message to the model if it exists
//	        if (message != null) {
//	            model.addAttribute("message", message);
//	            session.removeAttribute("message");  // Remove the message after displaying it
//	        }
//
//	        // Add error to the model if it exists
//	        if (error != null) {
//	            model.addAttribute("error", error);
//	            session.removeAttribute("error");  // Remove the error after displaying it
//	        }
//	    
		    return "home";
}
	  @GetMapping("/categories/{id}")
	    public String getProductsByCategory(@PathVariable Long id, Model model) {
	     
	        List<ProductEntity> products = productservices.getProductsByCategory(id);

	        model.addAttribute("products", products);

	        return "CategoryProduct"; 
	    }
	  
//	  @PostMapping("/Addwishlist/{id}")
//	  public String addToWishlist(@PathVariable Long id,HttpSession session) {
//		  try {
//			  System.out.println("coming");
//		        wishlistService.addProductToWishlist(id);
//		        session.setAttribute("message", "Product successfully added to wishlist");
//		    } catch (Exception e) {
//		 
//		        session.setAttribute("error", "An error occurred while adding product to wishlist");
//		    }
//		  return "home"; 
//	  }
	  @PostMapping("/Addwishlist/{id}")
	  public String addToWishlist(@PathVariable Long id, Principal principal, HttpSession session) {
		  try {
		        if (principal == null) {
		       
		            return "redirect:/login";
		        }

		    
		        String username = principal.getName();
		        System.out.println(username);

		       
		        boolean productAdded = wishlistService.addProductToWishlist(id, username);

		     
		        if (productAdded) {
		            session.setAttribute("message", "Product successfully added to wishlist");
		        } else {
		          
		            session.setAttribute("message", "Product is already in your wishlist");
		        }

		       
		        return "redirect:/";

		    } catch (Exception e) {
		       
		        session.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
		        return "redirect:/home";
		    }
	  }
	  
	  
	  @PostMapping("/Addcart/{id}")
	  public String addTocart(@PathVariable Long id,HttpSession session,Principal principal) {
	     System.out.println("in add to cart");
		  try {
		        
		        if (principal == null) {
		            return "redirect:/login";  
		        }

		    
		        boolean productAdded = cartService.addProductToCart(id);

		        if (productAdded) {
		            session.setAttribute("message", "Product successfully added to cart");
		        } else {
		            session.setAttribute("message", "Product is already in your cart");
		        }

		        return "redirect:/";  

		    } catch (Exception e) {
		       
		        session.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
		        return "redirect:/home"; 
		    }
		}

	
}	



	
	
	

