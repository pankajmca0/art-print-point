package com.artprintpoint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.VendorRequest;
import com.artprintpoint.app.entities.Purchase;
import com.artprintpoint.app.entities.Vendor;
import com.artprintpoint.app.service.PurchaseService;
import com.artprintpoint.app.service.VendorService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/vendors")
public class VendorController {

    private final VendorService vendorService;
    private final PurchaseService purchaseService;

    public VendorController(VendorService vendorService, PurchaseService purchaseService) {
        this.vendorService = vendorService;
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Vendor>> create(@RequestBody @Valid VendorRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Vendor created", vendorService.create(request), LocalDateTime.now(), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Vendor>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, vendorService.getAll(), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vendor>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, vendorService.getById(id), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}/purchases")
    public ResponseEntity<ApiResponse<List<Purchase>>> getVendorPurchases(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, purchaseService.getByVendor(id), LocalDateTime.now(), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Vendor>> update(@PathVariable Long id, @RequestBody @Valid VendorRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Vendor updated", vendorService.update(id, request), LocalDateTime.now(), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        vendorService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Vendor deactivated", null, LocalDateTime.now(), null));
    }
}
