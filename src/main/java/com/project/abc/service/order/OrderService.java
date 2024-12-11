package com.project.abc.service.order;

import com.project.abc.commons.Check;
import com.project.abc.commons.exceptions.http.BadRequestException;
import com.project.abc.commons.exceptions.http.OrderNotFoundException;
import com.project.abc.dto.order.OrderDTO;
import com.project.abc.dto.order.OrderDetailDTO;
import com.project.abc.dto.order.OrderSearchParamDTO;
import com.project.abc.dto.order.UpdateOrderStatusDTO;
import com.project.abc.model.category.Category;
import com.project.abc.model.item.Item;
import com.project.abc.model.offer.Offer;
import com.project.abc.model.order.Order;
import com.project.abc.model.order.OrderDetail;
import com.project.abc.model.order.Payment;
import com.project.abc.repository.order.OrderDetailRepository;
import com.project.abc.repository.order.OrderRepository;
import com.project.abc.repository.order.PaymentRepositoy;
import com.project.abc.security.Session;
import com.project.abc.service.item.ItemService;
import com.project.abc.service.offer.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PaymentRepositoy paymentRepositoy;

    @Autowired
    private OfferService offerService;

    public Order createOrder(OrderDTO orderDTO) {
        log.info("Place order by user {}", Session.getUser().getId());
        Order order = Order.init(orderDTO, Session.getUser());

        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderDetailDTO detailDTO : orderDTO.getOrderDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(UUID.randomUUID().toString());
            orderDetail.setQuantity(detailDTO.getQuantity());
            orderDetail.setOrder(order);

            if (detailDTO.getItem() != null) {
                Item item = itemService.getItemById(detailDTO.getItem().getId());
                orderDetail.setItem(item);
            }

            if (detailDTO.getOffer() != null) {
                Offer offer = offerService.getOfferById(detailDTO.getOffer().getId());
                orderDetail.setOffer(offer);
            }

            if (orderDetail.getItem() == null && orderDetail.getOffer() == null) {
                throw new IllegalArgumentException("Either item or offer must be provided for order details.");
            }

            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);

        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.saveAll(order.getOrderDetails());

        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(orderDTO.getPayment().getPaymentMethod());
        if (orderDTO.getPayment().getPaymentMethod().equals(Payment.PaymentMethod.ONLINE)) {
            payment.setPaymentDate(Instant.now());
            payment.setPaymentStatus(Payment.PaymentStatus.PAID);
        } else {
            payment.setPaymentDate(null);
            payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
        }
        payment.setOrder(savedOrder);
        Payment savedPayment = paymentRepositoy.save(payment);
        savedOrder.setPayment(savedPayment);

        return savedOrder;
    }

    public Order getOrderById(String orderId) {
        log.info("Get order by id = {}", orderId);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        Check.throwIfEmpty(orderOptional, new OrderNotFoundException("Order not found with Id : " + orderId));
        Order order = orderOptional.get();
        return order;
    }

    public Order updateOrderStatus(UpdateOrderStatusDTO updateOrderStatusDTO, String orderId) {
        log.info("update order status orderId {}", orderId);
        Order order = this.getOrderById(orderId);
        if (order.getStatus().equals(updateOrderStatusDTO.getStatus())) {
            throw new BadRequestException(orderId + "'s status is already exist");
        } else {
            order.setStatus(updateOrderStatusDTO.getStatus());
        }
        return orderRepository.save(order);
    }

    public Page<Order> searchOrders(OrderSearchParamDTO searchParamDTO) {
        log.info("get all orders");
        Pageable pageable = PageRequest.of(searchParamDTO.getPage(), searchParamDTO.getSize());
        return orderRepository.searchOrders(
                searchParamDTO.getOrderDate(),
                searchParamDTO.getOrderType(),
                searchParamDTO.getOrderStatus(),
                searchParamDTO.getUserId(),
                searchParamDTO.getPaymentStatus(),
                pageable
        );
    }

    public int getPendingOrderCount() {
        log.info("get pending order count");
        int pendingOrderCount = orderRepository.countByStatus(Order.OrderStatus.PENDING);
        return pendingOrderCount;
    }
}
