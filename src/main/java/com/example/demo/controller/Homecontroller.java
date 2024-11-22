package com.example.demo.controller;

import java.lang.ProcessBuilder.Redirect;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Aspect.EmailService;
import com.example.demo.Aspect.OtpService;
import com.example.demo.CustomHandler.SucessHandler;
import com.example.demo.Service.CartService;
import com.example.demo.Service.OfferServicea;
import com.example.demo.Service.Productservices;
import com.example.demo.Service.UserService;
import com.example.demo.Service.wishlistService;
import com.example.demo.model.AddressEntity;
import com.example.demo.model.CartEntity;
import com.example.demo.model.CartItemEntity;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.WishlistEntity;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@Autowired
	UserService userService;
	
	
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
	  public String addToWishlist(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
		  try {
		        if (principal == null) {
		       
		            return "redirect:/login";
		        }

		    
		        String username = principal.getName();
		        System.out.println(username);

		       
		        boolean productAdded = wishlistService.addProductToWishlist(id, username);

		     
		        if (productAdded) {

		        	  redirectAttributes.addFlashAttribute("message", "Product successfully added to wishlist");
		        } else {
		          
		        	 redirectAttributes.addFlashAttribute("message", "Product is already in your wishlist");
		        }

		       
		        return "redirect:/";

		    } catch (Exception e) {
		       
		    	 redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + e.getMessage());
		        return "redirect:/";
		    }
	  }
	  
	  
	  @PostMapping("/Addcart/{id}")
	  public String addTocart(@PathVariable Long id,RedirectAttributes redirectAttributes,Principal principal) {
	     System.out.println("in add to cart");
		  try {
		        
		        if (principal == null) {
		            return "redirect:/login";  
		        }

		    
		        boolean productAdded = cartService.addProductToCart(id);
		        

		        if (productAdded) {
		        	redirectAttributes.addFlashAttribute("message", "Product successfully added to cart");
		        } else {
		        	redirectAttributes.addFlashAttribute ("message", "Product is already in your cart");
		        }

		        return "redirect:/";  

		    } catch (Exception e) {
		       
		    	redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + e.getMessage());
		        return "redirect:/"; 
		    }
		}
	  
	  @GetMapping("/wishlist")
	  public String getWishist(Principal principal,Model model) {
		  if (principal == null) {
		       
		        return "redirect:/login";
		    }
		  
		List<WishlistEntity> wishlistEntity=  wishlistService.getbyuserId();
		model.addAttribute("wishlist", wishlistEntity);
			 return"wishlist";
		 }     
	  
	  
	  
	  @GetMapping("/cart")
	  public String getCart(Principal principal,Model model) {
		  
	System.out.println("gootten in cart get mapping");
		 if(principal==null) {
			 return"login";
		 }
		 List<CartItemEntity> cart=cartService.getbyUserId();
		 
		double totalPrice= cartService.calculateTotalprice(cart);
		 double Mrp = cartService.calculateExactPrice(cart);
//		 double totalPrice = cart.stream()
//				    .mapToDouble(item -> {
//				        ProductEntity product = item.getProduct();
//				        double price = (product.getOffer() != null)
//				                ? product.getOffer().getOfferPrice()
//				                : product.getPrice();
//				        return item.getQuantity() * price;
//				    })
//				    .sum();
		 
	      double saved=Mrp - totalPrice;
		 String formattedMrp = String.format("%.2f", saved);
		 
		  List<AddressEntity> addresses = userService.getAddressesByUserId();
		
		 System.out.println(addresses.get(0).getPincode());
		 model.addAttribute("totalPrice",totalPrice);
		 model.addAttribute("totalSaved",formattedMrp);
         model.addAttribute("cartItems",cart);
         model.addAttribute("addresses",addresses);
	    
			 return"cart";
		 }
	  
	  
	  
	  @PostMapping("/cart/updateQuantity")
	  public String updateQuantity(@RequestParam Long productId,@RequestParam Integer quantity) {
	  
	  	cartService.updateQuantity(productId,quantity);
	  	
	  	return "redirect:/cart";
	  }
	  @PostMapping("/cart/addressform")
	    public String submitForm(@ModelAttribute AddressEntity address) {
	      System.out.println("getting in acrt address form");
		  System.out.println("Received: City = " + address.getCity() + ", State = " + address.getState() + ", Postal Code = " + address.getPincode());
			return "redirect:/cart";
	    }
	  
	  

	
}	



	
	
	

