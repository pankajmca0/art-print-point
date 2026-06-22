package com.artprintpoint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.ProductRequest;
import com.artprintpoint.app.entities.Product;
import com.artprintpoint.app.service.ProductService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> create(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Product created", productService.create(request), LocalDateTime.now(), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, productService.getAll(), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, productService.getById(id), LocalDateTime.now(), null));
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ApiResponse<Product>> getByBarcode(@PathVariable String barcode) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, productService.getByBarcode(barcode), LocalDateTime.now(), null));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<ApiResponse<List<Product>>> getByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, productService.getByBranch(branchId), LocalDateTime.now(), null));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<Product>>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, productService.getByCategory(categoryId), LocalDateTime.now(), null));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<Product>>> getLowStock() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, productService.getLowStockProducts(), LocalDateTime.now(), null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> search(@RequestParam String name) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, productService.search(name), LocalDateTime.now(), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Product updated", productService.update(id, request), LocalDateTime.now(), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Product deactivated", null, LocalDateTime.now(), null));
    }
}
