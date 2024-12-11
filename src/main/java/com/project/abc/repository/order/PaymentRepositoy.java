package com.project.abc.repository.order;

import com.project.abc.model.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepositoy extends JpaRepository<Payment, String> {
}
