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
@Transactional
	public boolean saveOrder(Long addressId, String paymentMethod,HttpSession session) throws Exception {
		
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
	    
	    if (!"Cash on Delivery".equals(paymentMethod)) { 
	        session.setAttribute("order", order); 
	        return false;
	    }else {
	    	session.setAttribute("OrderId", order.getOrderId()); 
	    	  orderRepo.save(order);
	    	  return true;
	    }


	  
	 
	

	  //  cartService.clearCart(); 
			
	}

public void saveBankPaymnetOrder(OrderEntity order,HttpSession session) {
	session.setAttribute("OrderId", order.getOrderId()); 
	orderRepo.save(order);
	
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


 
public List<ChartData> getDeliveredOrdersChartData() {
   
    List<Object[]> results = orderItemRepo.findDeliveredOrdersGroupedByDate();

   
    List<ChartData> chartDataList = new ArrayList<>();
    for (Object[] result : results) {
        Date date = (Date) result[0];  
        double totalAmount = (Double) result[1]; 
        chartDataList.add(new ChartData(date, totalAmount));
    }

    return chartDataList;
}

public Long getTotalOrders() {
	
	return orderItemRepo.count();
	
}
public Long getTotalRevenue() {
    return orderItemRepo.sumPriceByStatus(OrderStatus.DELIVERED);
}
@Transactional
public List<OrderItemEntity> getOrderForResturamt() {
	
	return orderItemRepo.findOrderItemsByProductUserId(commonutil.getCurrentUserId());
}



}