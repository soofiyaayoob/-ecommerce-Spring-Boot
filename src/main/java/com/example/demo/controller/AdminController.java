package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Service.AdminServices;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.OfferServicea;
import com.example.demo.Service.Productservices;
import com.example.demo.Service.UserService;
import com.example.demo.model.AddressEntity;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.ProductEntity;

import com.example.demo.model.UserEntity;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminServices adminServices;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	Productservices productservices;
	
	@Autowired
	OfferServicea offerServicea;
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/dash")
	public String getDashboard(Model model) {
		List<CategoryEntity>  categories=categoryService.getAllcategries();
		 model.addAttribute("categories", categories);
		 model.addAttribute("adminForm", new UserEntity()); 
		return("admin");
	}
	
	
	 @PostMapping("/Addcategories")
	    public String createCategory(@RequestParam String categoryName, 
                @RequestParam("categoryImage") MultipartFile file,RedirectAttributes redirectAttributes) throws IOException {
		 
		 CategoryEntity categoryEntity=new CategoryEntity();
		 
		 if(!file.isEmpty()) {
			 String image=file.getOriginalFilename();
			byte[] data= file.getBytes();
			 categoryEntity.setImageName(image);
			 categoryEntity.setImageData(data);
		 }
	       boolean existCategory=categoryService.existCategory(categoryName);
	       
	       if(existCategory) {
	    	   redirectAttributes.addFlashAttribute("message", "Category already exist!");
	    	   redirectAttributes.addFlashAttribute("messageType", "error");  
	       }else {
	    	   categoryEntity.setName(categoryName);
	    	   categoryService.SaveCategory(categoryEntity);
	    	   redirectAttributes.addFlashAttribute("message","category added successfulyy!");
	    	   redirectAttributes.addFlashAttribute("messageType", "sucess");  
	    	   
	       }
	      
	       return "redirect:/admin/dash";
	        
	    }

	 
	 @PostMapping("/Addproduct")
	 public String addProduct(
	         @RequestParam("productName") String name,
	         @RequestParam("productPrice") Double price,
	         @RequestParam("productDescription") String description,
	         @RequestParam("productImage") MultipartFile image,
	        
	         @RequestParam("category") Long categoryId,RedirectAttributes redirectAttributes) throws IOException {  
		 CategoryEntity categoryEntity=categoryService.findCayegory(categoryId);
		  String productImage=image.getOriginalFilename();
		  
		  ProductEntity product = new ProductEntity();
		     product.setName(name);
		     product.setPrice(price);
		     product.setDescription(description);
		     product.setCategory(categoryEntity); 
		     product.setImageName(productImage);
		     product.setImageData(image.getBytes());

	     ProductEntity savedProduct =productservices.addProduct(product);

	     // Step 3: Save each size entry with a reference to the product
	   
	     redirectAttributes.addFlashAttribute("message","nw produced added succesfully!");
	     redirectAttributes.addFlashAttribute("messageType","success");  

	     return "redirect:/admin/dash";
	 }
	 
  

	     @PostMapping("/addAdmin")
	     public String addAdmin(@ModelAttribute("adminForm") UserEntity userEntity, Model model,RedirectAttributes 
	    		 redirectAttributes) {
	      
	    	  try {
	    	       
	    	         if (userEntity.getAddresses() != null && !userEntity.getAddresses().isEmpty()) {
	    	             AddressEntity address = userEntity.getAddresses().get(0);
	    	             address.setUser(userEntity); 
	    	         }

	    	         
	    	         adminServices.AddAdmin(userEntity);

	    	         
	    	         redirectAttributes.addFlashAttribute("message", "Admin added successfully");
	    	         redirectAttributes.addFlashAttribute("messageType", "success");  
	    	         return "redirect:/admin/dash"; 
	    	     } catch (Exception e) {
	    	        
	    	         redirectAttributes.addFlashAttribute("message", "Error occurred while adding the admin: " );
	    	         redirectAttributes.addFlashAttribute("messageType", "error");  
	    	         return "redirect:/admin/dash"; 
	    	     }
	    	 }
	     @PostMapping("Addoffer")
	     public String addOffer(@RequestParam ("productId") Long productId,
                 @RequestParam("discountPercentage") Double discountPercentage,
                 @RequestParam("offerEndDate") String offerEndDate,Model
                 model,RedirectAttributes 
                 redirectAttributes) {
	    	 

	    	 
	    	    try {
	    	        ProductEntity product = productservices.GetById(productId);
	    	        OfferEntity offerEntity = new OfferEntity();
	    	        
	    	        LocalDate endDate = LocalDate.parse(offerEndDate);
	    	        offerEntity.setProduct(product);
	    	        offerEntity.setDiscountPercentage(discountPercentage);
	    	        offerEntity.setOfferPrice(product.getPrice() * (1 - discountPercentage / 100));
	    	        offerEntity.setOfferEndDate(endDate);
	    	        
	    	        offerServicea.addNewOffer(offerEntity);
	    	        
	    	        redirectAttributes.addFlashAttribute("message", "Offer added successfully!");
	    	        redirectAttributes.addFlashAttribute("messageType", "success");
	    	    } catch (Exception e) {
	    	    	
	    	    	 redirectAttributes.addFlashAttribute("message", "An error occurred while adding the offer.");
	    	    	 redirectAttributes.addFlashAttribute("messageType", "error");  
	    	    }
	    	    
	    	    return "redirect:/admin/dash";  
	    	}
	     @GetMapping("/products")
	     public  String getAllprod(Model model) {
	    	List<ProductEntity> productEntities= productservices.getAllProducts();
              model.addAttribute("products", productEntities);
	
	        return "allProducts";
	    }
//	     @GetMapping("/users")
//	     public String listUsers(Model model) {
//	         List<UserEntity> users = userService.getAllUsers(); // Fetch users from the service
//	         model.addAttribute("users", users);
//	         return "Allusers"; 
//	     }
	     
//	     @PostMapping("/users/deactivate/{id}")
//	     @ResponseBody
//	     public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
//	         userService.deactivateUser(id); // Your service method to deactivate the user
//	         return ResponseEntity.ok().build();
//	     }
	     
	     
	     @GetMapping("/editProduct")
	     public String EditProduct(Model model,@RequestParam("productId") Long productId) {
	    	 ProductEntity productEntity=productservices.GetById(productId);
	    	 productservices.imageBase64(productEntity);
	    	 
	    	 List<CategoryEntity>  categories=categoryService.getAllcategries();
	    	 model.addAttribute("product",productEntity);
			 model.addAttribute("categories", categories);
	     	return "EditProduct";
	     }
	     
//	     @PostMapping("/update-product")
//	     public String postMethodName(@ModelAttribute ProductEntity productEntity,@RequestParam("productImage")
//	    		 MultipartFile file,  @RequestParam("category") Long categoryId,RedirectAttributes redirectAttributes) throws IOException {
//	     	productservices.updateproduct(productEntity,file,categoryId);
//	    	 redirectAttributes.addFlashAttribute("message", "product edited succsessfuly");
//	    	 
//	    return "redirect:/editProduct";
//	     }
	     
	     @PostMapping("/update-product")
	     public String postMethodName(
	             @ModelAttribute ProductEntity productEntity, 
	             @RequestParam("productImage") MultipartFile file,
	             @RequestParam("category") Long categoryId, 
	             RedirectAttributes redirectAttributes) {
	         try {
	             productservices.updateproduct(productEntity, file, categoryId);
	             redirectAttributes.addFlashAttribute("message", "Product edited successfully");
	         } catch (Exception e) {
	             redirectAttributes.addFlashAttribute("error", "Failed to edit product: " + e.getMessage());
	         }
	         return "EditProduct"; 
	     }
	     
     
	     @GetMapping("/users")
	     public String getAllUsers(Model model) {
	    	List<UserEntity> user= userService.getAllUsers();
	    	model.addAttribute("users",user);
	     	return "users";
	     }
	     
	
	     

	     @PostMapping("/users/toggleActive/{id}")
	     @ResponseBody
	     public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
	    	 System.out.println("hey there");
	         try {
	             userService.toggleActive(id);
	             return ResponseEntity.ok("User deactivated successfully.");
	         } catch (Exception e) {
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                  .body("Failed to deactivate user soo");
	         }
	     }
	     
	     

	     
	    	} 	
	    	 
	     

	     
	 


	 

