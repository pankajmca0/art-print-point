package com.artprintpoint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.CategoryRequest;
import com.artprintpoint.app.entities.Category;
import com.artprintpoint.app.service.CategoryService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> create(@RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Category created", categoryService.create(request), LocalDateTime.now(), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, categoryService.getAll(), LocalDateTime.now(), null));
    }

    @GetMapping("/root")
    public ResponseEntity<ApiResponse<List<Category>>> getRootCategories() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, categoryService.getRootCategories(), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<ApiResponse<List<Category>>> getSubcategories(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, categoryService.getSubcategories(id), LocalDateTime.now(), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> update(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Category updated", categoryService.update(id, request), LocalDateTime.now(), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Category deactivated", null, LocalDateTime.now(), null));
    }
}
