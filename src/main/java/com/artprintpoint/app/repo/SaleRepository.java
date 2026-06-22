package com.artprintpoint.app.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.artprintpoint.app.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCustomerId(Long customerId);
    List<Sale> findByBranchId(Long branchId);
    List<Sale> findBySaleDateBetween(LocalDate from, LocalDate to);
}
