package com.project.abc.repository.order;

import com.project.abc.model.category.Category;
import com.project.abc.model.order.Order;
import com.project.abc.model.order.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM orders o WHERE " +
            "(:orderDate IS NULL OR o.orderDate = :orderDate) AND " +
            "(:orderType IS NULL OR o.orderType = :orderType) AND " +
            "(:orderStatus IS NULL OR o.status = :orderStatus) AND " +
            "(:userId IS NULL OR o.user.id = :userId) AND " +
            "(:paymentStatus IS NULL OR o.payment.paymentStatus = :paymentStatus)")
    Page<Order> searchOrders(
            @Param("orderDate") Instant orderDate,
            @Param("orderType") Order.OrderType orderType,
            @Param("orderStatus") Order.OrderStatus orderStatus,
            @Param("userId") String userId,
            @Param("paymentStatus") Payment.PaymentStatus paymentStatus,
            Pageable pageable
    );

    int countByStatus(Order.OrderStatus status);
}
