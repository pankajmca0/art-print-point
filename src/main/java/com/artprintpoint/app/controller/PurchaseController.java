package com.artprintpoint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.PurchaseRequest;
import com.artprintpoint.app.entities.Purchase;
import com.artprintpoint.app.service.PurchaseService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Purchase>> create(@RequestBody @Valid PurchaseRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Purchase recorded & stock updated", purchaseService.create(request), LocalDateTime.now(), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Purchase>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, purchaseService.getAll(), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Purchase>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, purchaseService.getById(id), LocalDateTime.now(), null));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ApiResponse<List<Purchase>>> getByVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, purchaseService.getByVendor(vendorId), LocalDateTime.now(), null));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<ApiResponse<List<Purchase>>> getByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, purchaseService.getByBranch(branchId), LocalDateTime.now(), null));
    }
}
