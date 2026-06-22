package com.artprintpoint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.SaleRequest;
import com.artprintpoint.app.entities.Sale;
import com.artprintpoint.app.service.SaleService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Sale>> create(@RequestBody @Valid SaleRequest request, Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Sale completed & stock updated", saleService.create(request, email), LocalDateTime.now(), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Sale>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, saleService.getAll(), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sale>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, saleService.getById(id), LocalDateTime.now(), null));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<Sale>>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, saleService.getByCustomer(customerId), LocalDateTime.now(), null));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<ApiResponse<List<Sale>>> getByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, saleService.getByBranch(branchId), LocalDateTime.now(), null));
    }

    @PostMapping("/{id}/reverse")
    public ResponseEntity<ApiResponse<String>> reverseSale(@PathVariable Long id) {
        saleService.reverseSale(id);
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Sale reversed & stock restored", null, LocalDateTime.now(), null));
    }
}
