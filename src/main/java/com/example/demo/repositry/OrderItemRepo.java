package com.example.demo.repositry;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderItemEntity;
import com.example.demo.model.OrderItemEntity.OrderStatus;
@Repository
public interface OrderItemRepo extends JpaRepository<OrderItemEntity, Long>{

//	@Query( "SELECT o.orderedAt AS date, SUM(oi.price * oi.Quantity) AS totalAmount " +
//		       "FROM OrderItemEntity oi " + 
//		       "JOIN oi.order o " +
//		       "WHERE oi.status = 'DELIVERED' " +
//		       "GROUP BY o.orderedAt")
//		List<Map<String, Object>> getDeliveredOrderData();
	
	 @Query(value = "SELECT o.ordered_at, SUM(oi.price * oi.quantity) " +
             "FROM order_item oi " +
             "JOIN orders o ON oi.order_id = o.id " +
             "WHERE oi.status = 'DELIVERED' " +
             "GROUP BY o.ordered_at", nativeQuery = true)
List<Object[]> findDeliveredOrdersGroupedByDate();


@Query("SELECT SUM(o.price) FROM OrderItemEntity o WHERE o.status = :status")
    Long sumPriceByStatus(@Param("status") OrderStatus status);

@Query("SELECT oi FROM OrderItemEntity oi " +
        "JOIN oi.product p " +
        "WHERE p.user.id = :userId")
 List<OrderItemEntity> findOrderItemsByProductUserId(Long userId);
	
}