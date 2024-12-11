package com.project.abc.model.order;

import com.project.abc.dto.order.OrderDTO;
import com.project.abc.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @Column(name = "order_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;

    public enum OrderStatus {
        PENDING,
        COMPLETED,
        CANCELLED
    }

    public enum OrderType {
        DINE_IN,
        TAKE_AWAY,
        DELIVERY
    }

    public static Order init(OrderDTO dto, User user) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setOrderDate(dto.getOrderDate());
        order.setOrderType(dto.getOrderType());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);
        return order;
    }
}
