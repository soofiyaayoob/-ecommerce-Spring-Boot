package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Service.AdminServices;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.OfferServicea;
import com.example.demo.Service.Productservices;
import com.example.demo.model.AddressEntity;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.model.SizeEntity;
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
	
	
	@GetMapping("/dash")
	public String getDashboard(Model model) {
		List<CategoryEntity>  categories=categoryService.getAllcategries();
		 model.addAttribute("categories", categories);
		 model.addAttribute("adminForm", new UserEntity()); 
		return("admin");
	}
	
	
	 @PostMapping("/Addcategories")
	    public String createCategory(@RequestParam String categoryName, 
                @RequestParam("categoryImage") MultipartFile file,RedirectAttributes redirectAttributes) {
		 
		 CategoryEntity categoryEntity=new CategoryEntity();
		 
		 if(!file.isEmpty()) {
			 String image=file.getOriginalFilename();
			 categoryEntity.setImageName(image);
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
//	   Category category = new Category();
//       category.setName(categoryName);
//       category.setImageName(imageName);
//       categoryService.saveCategory(category);
	
// 
//	 @PostMapping("/register")
//	 public ResponseEntity<Object> CreateAdmin(@RequestBody UserEntity userEntity){
//		 
//		 adminServices.AddAdmin(userEntity);
//		 System.out.println("admin added");
//		// ResponseEntity.ok().body("Registration Successful");
//		 return ResponseEntity.
//			 ok().body("added");
//		 }
	 
	 
	 
//	 @GetMapping("/getCategories")
//	 public ResponseEntity<List<CategoryEntity>>getMethodName() {
//		 System.out.println("gettimg categories");
//		    List<CategoryEntity> categories = adminServices.getCategories();
//		    return ResponseEntity.ok(categories);  // This should return a JSON list
//		}
//	 
//	 @PostMapping("/addProduct")
//	 public ResponseEntity<?> addProduct(@RequestPart ProductEntity productEntity,@RequestPart MultipartFile multipartFile) throws IOException{
//		 adminServices.addProduct(productEntity,multipartFile);
//		 return ResponseEntity.ok().body("added");
//	 }
//	 @GetMapping("/search")
//	    public String searchProducts(@RequestParam("query") String query, Model model) {
//	        List<Product> searchResults = productService.searchProducts(query);
//	        model.addAttribute("products", searchResults);
//	        return "admin-dashboard";
//	    }
//
//	    @GetMapping("/products/add")
//	    public String showAddProductForm(Model model) {
//	        List<Category> categories = categoryService.getAllCategories();
//	        model.addAttribute("categories", categories);
//	        return "add-product";
//	    }
//
//	    @PostMapping("/products/add")
//	    public String addProduct(@ModelAttribute("product") Product product, @RequestParam("productImage") MultipartFile image) {
//	        // Save the product and the image
//	        productService.saveProduct(product, image);
//	        return "redirect:/admin-dashboard";
//	    }
	 
	 @PostMapping("/Addproduct")
	 public String addProduct(
	         @RequestParam("productName") String name,
	         @RequestParam("productPrice") Double price,
	         @RequestParam("productDescription") String description,
	         @RequestParam("productImage") MultipartFile image,
	         @RequestParam("productSize") List<String> sizes,
	         @RequestParam("category") Long categoryId,RedirectAttributes redirectAttributes) throws IOException {  
		 CategoryEntity categoryEntity=categoryService.findCayegory(categoryId);
		  String productImage=image.getOriginalFilename();
		  
		  ProductEntity product = new ProductEntity();
		     product.setName(name);
		     product.setPrice(price);
		     product.setDescription(description);
		     product.setCategory(categoryEntity); 
		     product.setImageName(productImage);
		     product.setImageDate(image.getBytes());

	     ProductEntity savedProduct =productservices.addProduct(product);

	     // Step 3: Save each size entry with a reference to the product
	     for (String size : sizes) {
	         SizeEntity productSize = new SizeEntity();
	         productSize.setSizeName(size);
	         productSize.setProduct(savedProduct);
	         productservices.addSizes(productSize);
	     }
	     redirectAttributes.addFlashAttribute("message","nw produced added succesfully!");
	     redirectAttributes.addFlashAttribute("messageType","success");  

	     return "redirect:/admin/dash"; // Redirect after saving
	 }
	 
  // Assuming you have a UserService to handle persistence

	     @PostMapping("/addAdmin")
	     public String addAdmin(@ModelAttribute("adminForm") UserEntity userEntity, Model model,RedirectAttributes 
	    		 redirectAttributes) {
	      
	    	  try {
	    	         // If the address list is not empty, set the user reference for each address
	    	         if (userEntity.getAddresses() != null && !userEntity.getAddresses().isEmpty()) {
	    	             AddressEntity address = userEntity.getAddresses().get(0);
	    	             address.setUser(userEntity); // Associate the user with the address
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

	       
	        for (ProductEntity productEntity : productEntities) {
	            if (productEntity.getImageDate() != null) {
	            	byte[] imageData = productEntity.getImageDate();  

	            	// Print the byte array to see the image data
	            	System.out.println("Image Data (byte array): " + Arrays.toString(imageData));
	            //	System.out.println(productEntity.getImageName().getBytes());
	            	System.out.println(productEntity.getImageDate());
//	                 if (productEntity.getImageDate() != null) {
	                String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageData);
	                productEntity.setImageBase64(base64Image);
	                System.out.println(productEntity.getImageBase64());
	            }
	            }
	   
	   

	        model.addAttribute("products", productEntities);
	       
	       
	        

	       
	        return "allProducts";
	    }
	     
	    	
	    	} 	
	    	 
	     

	     
	 


	 

