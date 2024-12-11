package com.project.abc.dto.order;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.order.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UpdateOrderStatusDTO extends BaseRequest {
    private Order.OrderStatus status;
}
