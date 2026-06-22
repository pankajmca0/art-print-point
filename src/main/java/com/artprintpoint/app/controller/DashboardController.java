package com.artprintpoint.app.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.DashboardResponse;
import com.artprintpoint.app.entities.Purchase;
import com.artprintpoint.app.entities.Sale;
import com.artprintpoint.app.service.DashboardService;
import com.artprintpoint.app.utils.AppConstants;

@RestController
@RequestMapping("/api/v1.0/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, dashboardService.getDashboard(), LocalDateTime.now(), null));
    }

    @GetMapping("/reports/sales")
    public ResponseEntity<ApiResponse<List<Sale>>> getSalesReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, dashboardService.getSalesReport(from, to), LocalDateTime.now(), null));
    }

    @GetMapping("/reports/purchases")
    public ResponseEntity<ApiResponse<List<Purchase>>> getPurchaseReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, null, dashboardService.getPurchaseReport(from, to), LocalDateTime.now(), null));
    }
}
