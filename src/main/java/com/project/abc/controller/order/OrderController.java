package com.project.abc.controller.order;

import com.project.abc.dto.category.CategoryCountDTO;
import com.project.abc.dto.order.OrderCountDTO;
import com.project.abc.dto.order.OrderDTO;
import com.project.abc.dto.order.OrderSearchParamDTO;
import com.project.abc.dto.order.UpdateOrderStatusDTO;
import com.project.abc.model.order.Order;
import com.project.abc.model.order.Payment;
import com.project.abc.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RequestMapping("/order")
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        orderDTO.validate();
        Order order = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(OrderDTO.init(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(OrderDTO.init(order));
    }

    @PutMapping("/update-order-status/{id}")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO,
            @PathVariable String id
    ) {
        updateOrderStatusDTO.validate();
        Order order = orderService.updateOrderStatus(updateOrderStatusDTO, id);
        OrderDTO orderDTO = OrderDTO.init(order);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDTO>> searchOrders(
            @RequestParam(required = false) Instant orderDate,
            @RequestParam(required = false) Order.OrderType orderType,
            @RequestParam(required = false) Order.OrderStatus orderStatus,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) Payment.PaymentStatus paymentStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        OrderSearchParamDTO searchParam = new OrderSearchParamDTO(
                orderDate,
                orderType,
                orderStatus,
                userId,
                paymentStatus,
                page,
                size
        );
        Page<Order> ordersPage = orderService.searchOrders(searchParam);
        Page<OrderDTO> orderDTOPage = ordersPage.map(OrderDTO::init);
        return ResponseEntity.ok(orderDTOPage);
    }

    @GetMapping("/pending-order-count")
    public ResponseEntity<OrderCountDTO> getPendingOrderCount() {
        int pendingOrderCount = orderService.getPendingOrderCount();
        OrderCountDTO orderCountDTO = new OrderCountDTO();
        orderCountDTO.setPendingOrderCount(pendingOrderCount);
        return ResponseEntity.ok(orderCountDTO);
    }
}
