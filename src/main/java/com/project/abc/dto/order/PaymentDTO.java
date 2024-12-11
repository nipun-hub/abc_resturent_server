package com.project.abc.dto.order;

import com.project.abc.commons.BaseRequest;
import com.project.abc.model.order.Payment;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@Slf4j
public class PaymentDTO extends BaseRequest {
    private String id;

    @NotNull(message = "Payment date is mandatory")
    private Instant paymentDate;

    @NotNull(message = "Amount is mandatory")
    private Double amount;

    @NotNull(message = "Payment method is mandatory")
    private Payment.PaymentMethod paymentMethod;

    @NotNull(message = "Payment status is mandatory")
    private Payment.PaymentStatus paymentStatus;

    public static PaymentDTO init(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        return dto;
    }
}
