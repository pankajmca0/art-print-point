package com.artprintpoint.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artprintpoint.app.dto.StockTransferRequest;
import com.artprintpoint.app.entities.*;
import com.artprintpoint.app.repo.*;

@Service
public class InventoryService {

    private final InventoryLogRepository inventoryLogRepository;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final StockTransferRepository stockTransferRepository;

    public InventoryService(InventoryLogRepository inventoryLogRepository, ProductRepository productRepository,
                            BranchRepository branchRepository, StockTransferRepository stockTransferRepository) {
        this.inventoryLogRepository = inventoryLogRepository;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.stockTransferRepository = stockTransferRepository;
    }

    public List<InventoryLog> getProductHistory(Long productId) {
        return inventoryLogRepository.findByProductId(productId);
    }

    public List<InventoryLog> getBranchInventory(Long branchId) {
        return inventoryLogRepository.findByBranchId(branchId);
    }

    public List<Product> getLowStockAlerts() {
        return productRepository.findLowStockProducts();
    }

    @Transactional
    public StockTransfer transferStock(StockTransferRequest request, Long userId) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for transfer");
        }

        Branch fromBranch = branchRepository.findById(request.getFromBranchId())
                .orElseThrow(() -> new RuntimeException("Source branch not found"));
        Branch toBranch = branchRepository.findById(request.getToBranchId())
                .orElseThrow(() -> new RuntimeException("Destination branch not found"));

        // Deduct from source (simplified: single product record)
        product.setQuantity(product.getQuantity() - request.getQuantity());
        productRepository.save(product);

        // Log OUT from source branch
        InventoryLog outLog = new InventoryLog();
        outLog.setProduct(product);
        outLog.setBranch(fromBranch);
        outLog.setMovementType("OUT");
        outLog.setQuantity(request.getQuantity());
        outLog.setReferenceType("TRANSFER");
        inventoryLogRepository.save(outLog);

        // Log IN to destination branch
        InventoryLog inLog = new InventoryLog();
        inLog.setProduct(product);
        inLog.setBranch(toBranch);
        inLog.setMovementType("IN");
        inLog.setQuantity(request.getQuantity());
        inLog.setReferenceType("TRANSFER");
        inventoryLogRepository.save(inLog);

        // Save transfer record
        StockTransfer transfer = new StockTransfer();
        transfer.setProduct(product);
        transfer.setFromBranch(fromBranch);
        transfer.setToBranch(toBranch);
        transfer.setQuantity(request.getQuantity());

        StockTransfer saved = stockTransferRepository.save(transfer);

        outLog.setReferenceId(saved.getId());
        inLog.setReferenceId(saved.getId());
        inventoryLogRepository.save(outLog);
        inventoryLogRepository.save(inLog);

        return saved;
    }
}
