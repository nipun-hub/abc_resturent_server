package com.project.abc.dto.order;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.order.Order;
import com.project.abc.model.order.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchParamDTO extends BaseRequest {
    private Instant orderDate;
    private Order.OrderType orderType;
    private Order.OrderStatus orderStatus;
    private String userId;
    private Payment.PaymentStatus paymentStatus;
    private int page = 0;
    private int size = 10;
}
