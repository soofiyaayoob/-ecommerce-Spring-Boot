package com.example.demo.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.Service.utilty.Commonutil;
import com.example.demo.model.CartItemEntity;
import com.example.demo.model.ChartData;
import com.example.demo.model.OrderEntity;
import com.example.demo.model.OrderItemEntity;
import com.example.demo.model.OrderItemEntity.OrderStatus;
import com.example.demo.model.UserEntity;
import com.example.demo.repositry.OrderItemRepo;
import com.example.demo.repositry.OrderRepo;
import com.example.demo.repositry.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;



@Service
public class OrderService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	Commonutil commonutil;
	
	@Autowired
	UserRepo userRepo;
	
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
//    System.out.println(orderItem.getStatus().name());
//    System.out.println(orderItem.getStatus());
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
@Transactional
public List<OrderItemEntity> orderByUser(Long id) {
  
   List<OrderEntity> orderEntity = orderRepo.findByUserId(id);
    

  
   List<OrderItemEntity> items = new ArrayList<>();
   for (OrderEntity order : orderEntity) {
      items.addAll(order.getItems());
   }
    
    
    return items;
}




public void canelOrder(Long itemId) {
	
OrderItemEntity item= orderItemRepo.findById(itemId).orElseThrow();

if (!"cash On Delivery".equalsIgnoreCase(item.getOrder().getPaymentType())) {
   
    UserEntity user = item.getOrder().getUser();
    user.setWallet(user.getWallet() + item.getPrice());
    userRepo.save(user);
}


item.setStatus(OrderStatus.CANCELED);
orderItemRepo.save(item); 
}


//public List<Map<String, Object>> getDeliveredOrdersForChart() {
//	
//	return orderItemRepo.getDeliveredOrderData();
//}


// Method to get delivered orders chart data
public List<ChartData> getDeliveredOrdersChartData() {
    // Fetching the data from OrderItemRepository
    List<Object[]> results = orderItemRepo.findDeliveredOrdersGroupedByDate();

    // Transforming results into ChartData objects
    List<ChartData> chartDataList = new ArrayList<>();
    for (Object[] result : results) {
        Date date = (Date) result[0];  // The date from the OrderEntity
        double totalAmount = (Double) result[1];  // The total amount for the date
        chartDataList.add(new ChartData(date, totalAmount));
    }

    return chartDataList;
}
}
