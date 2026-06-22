package com.artprintpoint.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.BranchRequest;
import com.artprintpoint.app.entities.Branch;
import com.artprintpoint.app.service.BranchService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Branch>> create(@RequestBody @Valid BranchRequest request) {
        Branch branch = branchService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Branch created", branch, LocalDateTime.now(), null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Branch>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, branchService.getAll(), LocalDateTime.now(), null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, branchService.getById(id), LocalDateTime.now(), null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> update(@PathVariable Long id, @RequestBody @Valid BranchRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Branch updated", branchService.update(id, request), LocalDateTime.now(), null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        branchService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, "Branch deactivated", null, LocalDateTime.now(), null));
    }
}
