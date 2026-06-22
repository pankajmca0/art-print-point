package com.artprintpoint.app.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.artprintpoint.app.entities.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByVendorId(Long vendorId);
    List<Purchase> findByBranchId(Long branchId);
    List<Purchase> findByPurchaseDateBetween(LocalDate from, LocalDate to);
}
