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
import com.example.demo.model.OrderItemEntity.OrderStatus;
import com.example.demo.repositry.OrderItemRepo;
import com.example.demo.repositry.OrderRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;



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
	
	@Autowired
	OrderItemRepo orderItemRepo;

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
	        
	      
	        item.setPrice(cart.getProduct().getOffer() != null ? cart.getProduct().getOffer().getOfferPrice() : cart.getProduct().getPrice());


	       
	        item.setQuantity(cart.getQuantity());



	        order.getItems().add(item);
	        
	        
	        
	        




	        
	    }

	  
	  orderRepo.save(order);
	  session.setAttribute("OrderId", order.getOrderId()); 

	  //  cartService.clearCart(); 
			
	}
@Transactional
	public List<OrderEntity> getAllOrders() {
		return orderRepo.findAll();
		
	}
@Transactional
public List<OrderItemEntity> getAllOrderItems() {
	return orderItemRepo.findAll();
}
public OrderItemEntity OrderItemfindById(Long itemId) {
  
    return orderItemRepo.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found for id: " + itemId));
}
@Transactional
public void updateStatus(OrderStatus newStatus, Long itemId) {
    
    OrderItemEntity orderItem = OrderItemfindById(itemId);
    System.out.println(orderItem.getStatus().name());
    System.out.println(orderItem.getStatus());
    System.out.println(newStatus);
   
    if (orderItem.getStatus().canTransitionTo(newStatus)) {
        orderItem.setStatus(newStatus);
        orderItemRepo.save(orderItem); 
       System.out.println(orderItem.getStatus().name());
       System.out.println(orderItem.getStatus());
    } else {
        
        throw new RuntimeException("Invalid status transition: " + orderItem.getStatus() + " -> " + newStatus);
    }
}
}
