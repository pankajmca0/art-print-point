package com.artprintpoint.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.artprintpoint.app.entities.StockTransfer;

public interface StockTransferRepository extends JpaRepository<StockTransfer, Long> {
    List<StockTransfer> findByFromBranchId(Long branchId);
    List<StockTransfer> findByToBranchId(Long branchId);
}
