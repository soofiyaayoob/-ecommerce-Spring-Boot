package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.example.demo.Service.OrderService;
import com.example.demo.Service.Productservices;
import com.example.demo.Service.UserService;
import com.example.demo.model.AddressEntity;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.ChartData;
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
	
	@Autowired
	OrderService orderService;
	
	
	@GetMapping("/dash")
	public String getDashboard(Model model) {
		List<CategoryEntity>  categories=categoryService.getAllcategries();
		
		 model.addAttribute("adminForm", new UserEntity()); 
		 
		 model.addAttribute("restuarntForm" ,new UserEntity());
		 
		return("admin");
	}
	
	
	 
	 
  

	     @PostMapping("/addAdmin")
	     public String addAdmin(@ModelAttribute("adminForm") UserEntity userEntity, Model model,RedirectAttributes 
	    		 redirectAttributes) {
	      
	    	  try {
	    	       
//	    	         if (userEntity.getAddresses() != null && !userEntity.getAddresses().isEmpty()) {
//	    	             AddressEntity address = userEntity.getAddresses().get(0);
//	    	             address.setUser(userEntity); 
//	    	         }

	    	         
	    	         adminServices.AddAdmin(userEntity);

	    	         
	    	         redirectAttributes.addFlashAttribute("message", "Admin added successfully");
	    	        
	    	         return "redirect:/admin/dash"; 
	    	     } catch (Exception e) {
	    	        
	    	         redirectAttributes.addFlashAttribute("error", e.getMessage() );
	    	        
	    	         return "redirect:/admin/dash"; 
	    	     }
	    	 }
	  
	     @GetMapping("/products")
	     public  String getAllprod(Model model) {
	    	List<ProductEntity> productEntities= productservices.getAllProducts();
              model.addAttribute("products", productEntities);
	
	        return "allProducts";
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
	     
	     
	     @PostMapping("/addRestaurant")
	     public String addresturant(@ModelAttribute("restuarntForm") UserEntity userEntity, Model model,RedirectAttributes 
	    		 redirectAttributes) {
	      
	    	  try {
	    	       
	    	        
	    	         
	    	         adminServices.AddRestaurant(userEntity);
	    	         redirectAttributes.addFlashAttribute("message", "Resturant added successfully");
	    	        
	    	         return "redirect:/admin/dash";
	    	     } catch (Exception e) {
	    	        
	    	         redirectAttributes.addFlashAttribute("error", e.getMessage() );
	    	         System.out.println(e.getMessage());
	    	        
	    	         return "redirect:/admin/dash"; 
	    	     }
	    	 }
	     
	     @GetMapping("/categories")
	     public String getMethodName(Model model) {
	    	 List<CategoryEntity> category=categoryService.getAllcategries();
	    	 model.addAttribute("categories",category);
	     	return "categoryAdmin";
	     }
	     

		 @PostMapping("/category/add")
		    public String createCategory(@RequestParam String categoryName, 
	                @RequestParam("categoryImage") MultipartFile file,RedirectAttributes redirectAttributes) throws IOException {
			 
			 CategoryEntity categoryEntity=new CategoryEntity();
			 
			 if(!file.isEmpty()) {
				 String image=file.getOriginalFilename();
				byte[] data= file.getBytes();
				 categoryEntity.setImageName(image);
				 categoryEntity.setImageData(data);
			 }
		       boolean existCategory=categoryService.existCategoryByname(categoryName);
		       
		       if(existCategory) {
		    	   redirectAttributes.addFlashAttribute("message", "Category already exist!");
		    	 
		       }else {
		    	   categoryEntity.setName(categoryName);
		    	   categoryService.SaveCategory(categoryEntity);
		    	   redirectAttributes.addFlashAttribute("message","category added successfulyy!");
		    	  
		    	   
		       }
		      
		       return "redirect:/admin/categories";
		        
		    }

		 @PostMapping ("/category/delete/{id}")
		 public String deleteCategory(@PathVariable Long id) {
		    
		     categoryService.deleteCategoryById(id);
		     return "redirect:/admin/categories"; 
		 }

		 
		 @PostMapping("/category/edit/{id}")
		 public String editCategory(@PathVariable("id") Long id,
		                             @RequestParam("categoryName") String categoryName,
		                             @RequestParam(value = "categoryImage") MultipartFile file,
		                             RedirectAttributes redirectAttributes) throws IOException {
			 

			 
			 try {
			        boolean isUpdated = categoryService.updateCategory(id, categoryName, file);
			        if (isUpdated) {
			            redirectAttributes.addFlashAttribute("message", "Category updated successfully!");
			        } else {
			            redirectAttributes.addFlashAttribute("message", "Category already exists!");
			        }
			    } catch (Exception e) {
			        redirectAttributes.addFlashAttribute("message", "An error occurred while updating the category.");
			    }
			 
			 return "redirect:/admin/categories";  
		   
		 }
		 
		 @GetMapping("/orders")
		 public String getorders(Model model) {
			
			 model.addAttribute("orders",orderService.getAllOrders());
		 	return "orderAdmin";
		 }
		 @GetMapping("/offers")
		 public String getoffers(Model model) {
			
			 model.addAttribute("offers",offerServicea.getAllOffers());
		 	return "offerAdmin";
		 }
		 @GetMapping("/chart")
		 public String getchart() {
		 	return"chart";
		 }
		 

		 @GetMapping("/delivered-chart-data")
		 @ResponseBody
		    public List<ChartData> getDeliveredOrderData() {
		       System.out.println("gotten here chart ");
		        return orderService.getDeliveredOrdersChartData();
		    }
		 
		 

//		 
//		   @GetMapping("/delivered-chart-data")
//		    public List<Map<String, Object>> getDeliveredChartData() {
//		        return orderService.getDeliveredOrdersForChart();
//		    }
//
	    	}


	    	 
	     

	     
	 


	 

