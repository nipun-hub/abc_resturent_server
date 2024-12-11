package com.project.abc.dto.order;

import com.project.abc.commons.BaseRequest;
import com.project.abc.dto.item.ItemDTO;
import com.project.abc.dto.offer.OfferDTO;
import com.project.abc.dto.user.UserDTO;
import com.project.abc.model.order.Order;
import com.project.abc.model.order.OrderDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class OrderDTO extends BaseRequest {
    private String id;

    @NotNull(message = "Order date is mandatory")
    private Instant orderDate;

    @NotNull(message = "Order type is mandatory")
    private Order.OrderType orderType;

    @NotNull(message = "Total amount is mandatory")
    private Double totalAmount;

    private Order.OrderStatus status;

    @NotNull(message = "User is mandatory")
    private UserDTO user;

    private Set<OrderDetailDTO> orderDetails;

    private PaymentDTO payment;

    public static OrderDTO init(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderType(order.getOrderType());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setUser(UserDTO.init(order.getUser()));

        Set<OrderDetailDTO> orderDetailDTOs = new HashSet<>();
        for (OrderDetail detail : order.getOrderDetails()) {
            OrderDetailDTO detailDTO = new OrderDetailDTO();
            detailDTO.setId(detail.getId());
            detailDTO.setQuantity(detail.getQuantity());
            if (detail.getItem() != null) {
                detailDTO.setItem(ItemDTO.initWithCategory(detail.getItem()));
            }
            if (detail.getOffer() != null) {
                detailDTO.setOffer(OfferDTO.init(detail.getOffer()));
            }
            orderDetailDTOs.add(detailDTO);
        }
        orderDTO.setOrderDetails(orderDetailDTOs);

        if (order.getPayment() != null) {
            orderDTO.setPayment(PaymentDTO.init(order.getPayment()));
        }

        return orderDTO;
    }
}
