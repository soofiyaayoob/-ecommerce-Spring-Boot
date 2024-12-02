package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CategoryEntity;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.ProductEntity;


@Controller
@RequestMapping("/restaurant")
public class ResturantController {
	
	@GetMapping("/main")
	public String getMainpage() {
		return "resturant";
	}
	
@GetMapping("/menu-management")
public String getMenu(Model model) {
	model.addAttribute("new product", new ProductEntity());
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
}
