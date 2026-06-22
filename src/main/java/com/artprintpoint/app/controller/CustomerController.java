package com.artprintpoint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.CustomerRequest;
import com.artprintpoint.app.entities.Customer;
import com.artprintpoint.app.entities.Sale;
import com.artprintpoint.app.service.CustomerService;
import com.artprintpoint.app.service.SaleService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final SaleService saleService;

    public CustomerController(CustomerService customerService, SaleService saleService) {
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> create(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Customer created", customerService.create(request), LocalDateTime.now(), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, customerService.getAll(), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, customerService.getById(id), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}/purchases")
    public ResponseEntity<ApiResponse<List<Sale>>> getCustomerPurchases(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, saleService.getByCustomer(id), LocalDateTime.now(), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> update(@PathVariable Long id, @RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Customer updated", customerService.update(id, request), LocalDateTime.now(), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Customer deactivated", null, LocalDateTime.now(), null));
    }
}
