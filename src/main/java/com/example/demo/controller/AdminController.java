package com.example.demo.controller;

import java.io.IOException;
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
import com.example.demo.Service.Productservices;
import com.example.demo.model.AddressEntity;
import com.example.demo.model.CategoryEntity;
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
	
	
	@GetMapping("/dash")
	public String getDashboard(Model model) {
		List<CategoryEntity>  categories=categoryService.getAllcategries();
		 model.addAttribute("categories", categories);
		 model.addAttribute("adminForm", new UserEntity()); 
		return("admin");
	}
	
	
	 @PostMapping("/categories/add")
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
	       }else {
	    	   categoryEntity.setName(categoryName);
	    	   categoryService.SaveCategory(categoryEntity);
	    	   redirectAttributes.addFlashAttribute("message","category added successfulyy!");
	    	   
	       }
	      
	       return "redirect:/admin";
	        
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
	         @RequestParam("category") Long categoryId,RedirectAttributes redirectAttributes) {  
		 CategoryEntity categoryEntity=categoryService.findCayegory(categoryId);
		  String productImage=image.getOriginalFilename();
		  
		  ProductEntity product = new ProductEntity();
		     product.setName(name);
		     product.setPrice(price);
		     product.setDescription(description);
		     product.setCategory(categoryEntity); 
		     product.setImageName(productImage);

	     ProductEntity savedProduct =productservices.addProduct(product);

	     // Step 3: Save each size entry with a reference to the product
	     for (String size : sizes) {
	         SizeEntity productSize = new SizeEntity();
	         productSize.setSizeName(size);
	         productSize.setProduct(savedProduct);
	         productservices.addSizes(productSize);
	     }
	     redirectAttributes.addFlashAttribute("message","category added successfulyy!");

	     return "redirect:/admin"; // Redirect after saving
	 }
	 
  // Assuming you have a UserService to handle persistence

	     @PostMapping("/add")
	     public String addAdmin(@ModelAttribute("adminForm") UserEntity userEntity, Model model,RedirectAttributes 
	    		 redirectAttributes) {
	      
	    	  try {
	    	         // If the address list is not empty, set the user reference for each address
	    	         if (userEntity.getAddresses() != null && !userEntity.getAddresses().isEmpty()) {
	    	             AddressEntity address = userEntity.getAddresses().get(0);
	    	             address.setUser(userEntity); // Associate the user with the address
	    	         }

	    	         
	    	         adminServices.AddAdmin(userEntity);

	    	         // Add success message to the model or redirect attributes
	    	         redirectAttributes.addFlashAttribute("message", "Admin added successfully");
	    	         return "redirect:/admin"; // Redirect to a success page or some other appropriate page
	    	     } catch (Exception e) {
	    	         // Catch any exceptions (like constraint violations, database errors, etc.)
	    	         redirectAttributes.addFlashAttribute("message", "Error occurred while adding the admin: " + e.getMessage());
	    	         return "redirect:/admin"; // Redirect back to the form page with the error message
	    	     }
	    	 }

	     
	 }


	 

