package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.Service.utilty.Commonutil;
import com.example.demo.model.CartItemEntity;
import com.example.demo.model.OrderEntity;
import com.example.demo.model.OrderItemEntity;
import com.example.demo.repositry.OrderRepo;

import jakarta.servlet.http.HttpSession;



@Service
public class OrderService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	Commonutil commonutil;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	OrderRepo orderRepo;

	public void saveOrder(Long addressId, String paymentMethod,HttpSession session) throws Exception {
		
		// Create a new order entity
	    OrderEntity order = new OrderEntity();
	    
	 
	    order.setShippingAddress(userService.getAddressById(addressId));
	    order.setOrderId(UUID.randomUUID().toString());
	    order.setPaymentType(paymentMethod);
	    order.setUser(commonutil.getCurrentUser());
	    
	   
	    List<CartItemEntity> carts = cartService.getbyUserId();
	    
	   
	    for (CartItemEntity cart : carts) {
	     
	        OrderItemEntity item = new OrderItemEntity();

	       
	        item.setOrder(order);
	       
	        item.setProduct(cart.getProduct());
	        
	       
	        item.setPrice(cart.getProduct().getOffer().getOfferPrice());

	       
	        item.setQuantity(cart.getQuantity());



	        order.getItems().add(item);
	        
	        
	        




	        
	    }

	  
	  orderRepo.save(order);
	  session.setAttribute("OrderId", order.getOrderId()); 

	    cartService.clearCart(); 
			
	}
	
	

//	
//	 public boolean updateStatus(OrderStatus newStatus) {
//	        if (this.status.canTransitionTo(newStatus)) {
//	            this.status = newStatus;
//	            return true;
//	        } else {
//	            System.out.println("Invalid status transition: " + this.status + " -> " + newStatus);
//	            return false;
//	        }
//	    }
}
