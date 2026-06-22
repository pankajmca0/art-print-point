package com.artprintpoint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.StockTransferRequest;
import com.artprintpoint.app.entities.InventoryLog;
import com.artprintpoint.app.entities.Product;
import com.artprintpoint.app.entities.StockTransfer;
import com.artprintpoint.app.service.InventoryService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/product/{productId}/history")
    public ResponseEntity<ApiResponse<List<InventoryLog>>> getProductHistory(@PathVariable Long productId) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, inventoryService.getProductHistory(productId), LocalDateTime.now(), null));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<ApiResponse<List<InventoryLog>>> getBranchInventory(@PathVariable Long branchId) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, inventoryService.getBranchInventory(branchId), LocalDateTime.now(), null));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<Product>>> getLowStockAlerts() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, inventoryService.getLowStockAlerts(), LocalDateTime.now(), null));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<StockTransfer>> transferStock(@RequestBody @Valid StockTransferRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Stock transferred successfully", inventoryService.transferStock(request, null), LocalDateTime.now(), null));
    }
}
