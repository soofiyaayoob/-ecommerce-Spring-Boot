package com.example.demo.controller;

import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.AttributeNotFoundException;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Aspect.EmailService;
import com.example.demo.Aspect.OtpService;
import com.example.demo.CustomHandler.SucessHandler;
import com.example.demo.Principle.UserPrincipleTaamsmaak;
import com.example.demo.Service.CartService;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.OfferServicea;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.Productservices;
import com.example.demo.Service.UserService;
import com.example.demo.Service.wishlistService;
import com.example.demo.model.AddressEntity;
import com.example.demo.model.CartEntity;
import com.example.demo.model.CartItemEntity;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.OrderItemEntity;
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
import org.hibernate.internal.build.AllowSysOut;
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
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	OrderService orderService;
	
	 @GetMapping("/")
	    public String home(Model model,HttpSession session) {
		 List<OfferEntity>offers=offerService.get4Greatoffers();
		
		List<ProductEntity>latestproducts=productservices.LatestProduct();
		List<ProductEntity>Allproducts=productservices.getAllProducts();
		List<CategoryEntity>Allcategory=categoryService.getAllcategries();
		
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
	  @GetMapping("/home/categories/{id}")
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
	  @PostMapping("/home/Addwishlist/{id}")
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
	  
	  
	  @PostMapping("/home/Addcart/{id}")
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
	  
	  @GetMapping("/home/wishlist")
	  public String getWishist(Principal principal,Model model) {
		  if (principal == null) {
		       
		        return "redirect:/login";
		    }
		  
		List<WishlistEntity> wishlistEntity=  wishlistService.getbyuserId();
		model.addAttribute("wishlist", wishlistEntity);
			 return"wishlist";
		 }     
	  
	  
	  
	  @GetMapping("/home/cart")
	  public String getCart(Principal principal,Model model,RedirectAttributes redirectAttributes) {
		  
	System.out.println("gootten in cart get mapping");
//		 if(principal==null||!((UserEntity) principal).getRole().equals("USER")) {
//			 System.out.println(((UserEntity) principal).getRole());
//			 redirectAttributes.addFlashAttribute("message","you need to craete a account frst");
//			 return"login";
//		 }
	 if (principal == null) {
	       
	        return "redirect:/login";
	    }
		 
	
		  
		 List<CartItemEntity> cart;
		try {
			cart = cartService.getbyUserId();
			double totalPrice= cartService.calculateTotalprice(cart);
			 double Mrp = cartService.calculateExactPrice(cart);

			 
		      double saved=Mrp - totalPrice;
			 String formattedMrp = String.format("%.2f", saved);
			 
			  List<AddressEntity> addresses = userService.getAddressesByUserId();
			
			
			 model.addAttribute("totalPrice",totalPrice);
			 model.addAttribute("totalSaved",formattedMrp);
	         model.addAttribute("cartItems",cart);
	         model.addAttribute("addresses",addresses);
		    
				 return"cart";
		} catch (Exception e) {
			
			redirectAttributes.addFlashAttribute("message",e.getMessage());
			 return"cart";
		}
		 
		
			
		 }
	  
	  @PostMapping("/home/cart/delete/{id}")
	  public String deleteProduct(@PathVariable Long id) {
	      try {
	        
	          cartService.deleteProductById(id);
	          System.out.println("deleted");
	      } catch (Exception e) {
	       
	          System.out.println("Error deleting product with ID " + id + ": " + e.getMessage());
	        
	          return "redirect:/cart?error=true";
	      }
	    
	      return "redirect:/cart";
	  }

	  
	  
	  @PostMapping("/cart/updateQuantity")
	  public String updateQuantity(@RequestParam Long productId,@RequestParam Integer quantity) {
	  
	  	cartService.updateQuantity(productId,quantity);
	  	
	  	return "redirect:/cart";
	  }
	  @PostMapping("/home/cart/address/add")
	    public String submitForm(@ModelAttribute AddressEntity address,@AuthenticationPrincipal UserPrincipleTaamsmaak userpriniciple) {
	      System.out.println("getting in acrt address form");
	      if (userpriniciple == null ||userpriniciple .getUser() == null) {
	          throw new IllegalStateException("No authenticated user found.");
	      }
	      address.setUser(userpriniciple.getUser());
		  userService.saveAddress(address);
			return "redirect:/cart";
	    }
	  
	  @GetMapping("/home/profile")
		public String getprofile(Model model) {
		UserEntity user= userService.findUser();
		model.addAttribute("newAddress",new AddressEntity());
		  model.addAttribute("user",user);
		  List<OrderItemEntity> items=orderService.orderByUser(user.getId());
		  model.addAttribute("items",items);
		  
			return "profile";
		}
	  
	  @PostMapping("/home/profile/updateUser")
	  public String updateUser(@ModelAttribute("user") UserEntity user,@AuthenticationPrincipal UserPrincipleTaamsmaak principleTaamsmaak) {
		  System.out.println(user.getId());
		  user.setPassword(principleTaamsmaak.getPassword());
	      userService.updateUser(user); 
	      
	      return "redirect:/profile"; 
	  }
	  
	  @PostMapping("/profile/addaddress")
	  public String addAddress(@ModelAttribute AddressEntity address,@AuthenticationPrincipal UserPrincipleTaamsmaak principleTaamsmaak) {
		  System.out.println("got end adda adress");
		  address.setUser(principleTaamsmaak.getUser());
	      userService.saveAddress(address); 
	      return "redirect:/profile";
	  }
	  
	

	  @PostMapping("/home/profile/resetPassword")
	    public String resetPassword(
	            @RequestParam("currentPassword") String currentPassword,
	            @RequestParam("newPassword") String newPassword,RedirectAttributes redirectAttributes) {
	          
	       
	          try {
	     
	        	   userService.changePassword(currentPassword,newPassword);
	             
	              redirectAttributes.addFlashAttribute("message", "Password changed successfully.");
	              System.out.println();
	            

	          } catch (Exception e) {
	             
	              redirectAttributes.addFlashAttribute("error", e.getMessage());
	             
	          }
	          System.out.println("Flash message: " + redirectAttributes.getFlashAttributes());
	          return "redirect:/profile"; 
	       
	      }


	  
	  @GetMapping("/home/search")
	    public String listFoods(@RequestParam(value = "query", required = false) String query, Model model,RedirectAttributes redirectAttributes) {
		  System.out.println("hey gooten in serach");
		  System.out.println(query);
	      try {  List<ProductEntity> foods;
	        
          foods = productservices.searchFoods(query);
      
      model.addAttribute("products", foods);
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error",e.getMessage());
			return("redirect:/");
		}
	        
	        return "productBySearch";
	    }
	  
	  @PostMapping("/home/search/Addcart")
	    public String addToCart(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
	       
	   
	        try {
				cartService.addProductToCart(productId);
				  redirectAttributes.addFlashAttribute("success", "Product added to cart successfully!");
			} catch (AttributeNotFoundException e) {
				redirectAttributes.addFlashAttribute("error",e.getMessage());
				e.printStackTrace();
			}

	      
	        return "redirect:/home/search"; 
	    }
	
	
}	



	
	
	

