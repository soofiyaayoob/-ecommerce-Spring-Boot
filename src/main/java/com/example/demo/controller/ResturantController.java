package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Service.CategoryService;
import com.example.demo.Service.Productservices;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.ProductEntity;


@Controller
@RequestMapping("/restaurant")
public class ResturantController {
	
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	Productservices productservices;
	
	@GetMapping("/main")
	public String getMainpage() {
		return "resturant";
	}
	
@GetMapping("/menu-management")
public String getMenu(Model model) {
	model.addAttribute("product", new ProductEntity());
	model.addAttribute("categories",categoryService.getAllcategries());
	model.addAttribute("products",productservices.getAllProducts());
    return"menu";
}
//@PostMapping("/Addproduct")
//public String addProduct(
//        @RequestParam("productName") String name,
//        @RequestParam("productPrice") Double price,
//        @RequestParam("productDescription") String description,
//        @RequestParam("productImage") MultipartFile image,
//       
//        @RequestParam("category") Long categoryId,RedirectAttributes redirectAttributes) throws IOException {  
//	 CategoryEntity categoryEntity=categoryService.findCayegory(categoryId);
//	  String productImage=image.getOriginalFilename();
//	  
//	  ProductEntity product = new ProductEntity();
//	     product.setName(name);
//	     product.setPrice(price);
//	     product.setDescription(description);
//	     product.setCategory(categoryEntity); 
//	     product.setImageName(productImage);
//	     product.setImageData(image.getBytes());
//
//    ProductEntity savedProduct =productservices.addProduct(product);
//
//    // Step 3: Save each size entry with a reference to the product
//  
//    redirectAttributes.addFlashAttribute("message","nw produced added succesfully!");
//    redirectAttributes.addFlashAttribute("messageType","success");  
//
//    return "redirect:/admin/dash";
//}
//@PostMapping("Addoffer")
//public String addOffer(@RequestParam ("productId") Long productId,
//        @RequestParam("discountPercentage") Double discountPercentage,
//        @RequestParam("offerEndDate") String offerEndDate,Model
//        model,RedirectAttributes 
//        redirectAttributes) {
//	 
//
//	 
//	    try {
//	        ProductEntity product = productservices.GetById(productId);
//	        OfferEntity offerEntity = new OfferEntity();
//	        
//	        LocalDate endDate = LocalDate.parse(offerEndDate);
//	        offerEntity.setProduct(product);
//	        offerEntity.setDiscountPercentage(discountPercentage);
//	        offerEntity.setOfferPrice(product.getPrice() * (1 - discountPercentage / 100));
//	        offerEntity.setOfferEndDate(endDate);
//	        
//	        offerServicea.addNewOffer(offerEntity);
//	        
//	        redirectAttributes.addFlashAttribute("message", "Offer added successfully!");
//	        redirectAttributes.addFlashAttribute("messageType", "success");
//	    } catch (Exception e) {
//	    	
//	    	 redirectAttributes.addFlashAttribute("message", "An error occurred while adding the offer.");
//	    	 redirectAttributes.addFlashAttribute("messageType", "error");  
//	    }
//	    
//	    return "redirect:/admin/dash";  
//	}

@PostMapping("/Addproduct")
public String addProduct(@ModelAttribute("product") ProductEntity product,
                         @RequestParam("productImage") MultipartFile imageFile) {
    try {
        productservices.updateProduct(product, imageFile);
    } catch (Exception e) {
        e.printStackTrace();
      
    }
    return "redirect:/restaurant/menu-management"; 
}
@PostMapping("/updateProduct")
public String updateProduct(@ModelAttribute("product") ProductEntity product,
		 @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                            RedirectAttributes redirectAttributes) {
System.out.println("gotten heere ");
System.out.println(product.getName());
    try {
        
    	 productservices.updateProduct(product, productImage);
    	 System.out.println("adedded");
        redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
        return "redirect:/restaurant/menu-management"; // Redirect to the product list or any other page

    } catch (Exception e) {
        System.out.println(e.getMessage());
        redirectAttributes.addFlashAttribute("error", "Failed to update product!");
        return "redirect:/restaurant/menu-management";
    }
}
}
