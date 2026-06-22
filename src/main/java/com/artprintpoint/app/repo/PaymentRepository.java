package com.artprintpoint.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.artprintpoint.app.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findBySaleId(Long saleId);
    List<Payment> findByPurchaseId(Long purchaseId);
}
