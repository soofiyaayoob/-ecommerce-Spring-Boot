package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Service.CategoryService;
import com.example.demo.Service.OfferServicea;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.Productservices;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.OfferEntity;
import com.example.demo.model.OrderItemEntity.OrderStatus;
import com.example.demo.model.ProductEntity;


@Controller
@RequestMapping("/restaurant")
public class ResturantController {
	
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	Productservices productservices;
	
	@Autowired
	OfferServicea offerServicea;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/main")
	public String getMainpage() {
		return "resturant";
	}
	
@GetMapping("/menu-management")
public String getMenu(Model model) {
	model.addAttribute("product", new ProductEntity());
	model.addAttribute("categories",categoryService.getAllcategries());
	model.addAttribute("products",productservices.getproductbyuserid());
    return"menu";
}

@PostMapping("product/add")
public String addProduct(@ModelAttribute("product") ProductEntity product,
                         @RequestParam("productImage") MultipartFile imageFile) {
    try {
        productservices.addProduct(product, imageFile);
    } catch (Exception e) {
        e.printStackTrace();
      
    }
    return "redirect:/restaurant/menu-management"; 
}

@PostMapping("product/Updtae")
public String updateProduct(@ModelAttribute("product") ProductEntity product,
                             @RequestParam(value = "productImage", required = false) MultipartFile productImage,RedirectAttributes attributes) throws IOException {
	
	

    productservices.updateProduct(product,productImage);
attributes.addFlashAttribute("message","produxt updated successfuly");
    return "redirect:/restaurant/menu-management"; 
}

@PostMapping("product/delete/{id}")
public String deleteItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    boolean deleted = productservices.deleteById(id);
   
       redirectAttributes.addFlashAttribute("message","deleted suucessfulyy");
  
    	 redirectAttributes.addFlashAttribute("error", "Item not found");
    
    return "redirect:/restaurant/menu-management"; 
}


@GetMapping("/offer-management")
public String getOfferManagementPage(Model model) {
 List<OfferEntity> offers= offerServicea.getofferbyuserid();
  model.addAttribute("offers",offers);
    return "offer"; 
}

@PostMapping("/offer/add")
public String addOffer(
        @RequestParam("productId") Long productId,
        @RequestParam("discountPercentage") Double discountPercentage,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        RedirectAttributes redirectAttributes) {
    try {
        offerServicea.addOffer(productId, discountPercentage, endDate);
       redirectAttributes.addFlashAttribute("message", "Offer added successfully!");
    } catch (IllegalArgumentException e) {
    	redirectAttributes.addFlashAttribute ("error", "Error: " + e.getMessage());
    } catch (Exception e) {
    	redirectAttributes.addFlashAttribute("error", "Unexpected error: " + e.getMessage());
    }
    return "redirect:/restaurant/offer-management"; 
  }

@PostMapping("/delete/{id}")
public String deleteOffer(@PathVariable("id") Long id) {
  
        offerServicea.deleteOffer(id);  
        return "redirect:/restaurant/offer-management"; 
   
}

@GetMapping("/order-management")
public String getorders(Model model) {
	
	//model.addAttribute("orders",orderService.getAllOrders());
	model.addAttribute("orderItems",orderService.getOrderForResturamt());
  return"order";
}
@PostMapping("/updateStatus/{id}")
public String updateOrderStatus(@PathVariable("id") Long itemId, 
                                @RequestParam("status") OrderStatus status, 
                                Model model, RedirectAttributes redirectAttributes) {
    System.out.println("Attempting to update status for item: " + itemId + " to " + status);
   
    try {
        
        orderService.updateStatus(status, itemId); 
        redirectAttributes.addFlashAttribute("message", "Order status updated successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
    }

    return "redirect:/restaurant/order-management"; 
}



}
