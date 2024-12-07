package com.example.demo.repositry;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderItemEntity;
@Repository
public interface OrderItemRepo extends JpaRepository<OrderItemEntity, Long>{
//	  @Query("SELECT o.order.orderedAt AS date, SUM(o.price * o.quantity) AS totalAmount " +
//	           "FROM OrderItemEntity o " +
//	           "WHERE o.status = 'DELIVERED' " +
//	           "GROUP BY o.order.orderedAt")
//	    List<Map<String, Object>> getDeliveredOrderData();
	
	 @Query(value = "SELECT o.ordered_at, SUM(oi.price * oi.quantity) " +
             "FROM order_item oi " +
             "JOIN orders o ON oi.order_id = o.id " +
             "WHERE oi.status = 'DELIVERED' " +
             "GROUP BY o.ordered_at", nativeQuery = true)
List<Object[]> findDeliveredOrdersGroupedByDate();
}
