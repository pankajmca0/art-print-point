package com.artprintpoint.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.artprintpoint.app.entities.InventoryLog;

public interface InventoryLogRepository extends JpaRepository<InventoryLog, Long> {
    List<InventoryLog> findByProductId(Long productId);
    List<InventoryLog> findByBranchId(Long branchId);
}
