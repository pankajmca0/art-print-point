package com.artprintpoint.app.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.artprintpoint.app.dto.DashboardResponse;
import com.artprintpoint.app.entities.Product;
import com.artprintpoint.app.entities.Purchase;
import com.artprintpoint.app.entities.Sale;
import com.artprintpoint.app.repo.ProductRepository;
import com.artprintpoint.app.repo.PurchaseRepository;
import com.artprintpoint.app.repo.SaleRepository;

@Service
public class DashboardService {

    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    public DashboardService(SaleRepository saleRepository, PurchaseRepository purchaseRepository,
                            ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
    }

    public DashboardResponse getDashboard() {
        List<Sale> allSales = saleRepository.findAll();
        List<Purchase> allPurchases = purchaseRepository.findAll();
        List<Product> lowStockProducts = productRepository.findLowStockProducts();

        BigDecimal totalSales = allSales.stream()
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPurchases = allPurchases.stream()
                .map(Purchase::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal profit = totalSales.subtract(totalPurchases);

        Map<String, BigDecimal> branchWiseSales = new HashMap<>();
        allSales.stream()
                .filter(s -> s.getBranch() != null)
                .forEach(s -> branchWiseSales.merge(
                        s.getBranch().getBranchName(), s.getTotalAmount(), BigDecimal::add));

        List<DashboardResponse.ProductStockAlert> alerts = lowStockProducts.stream()
                .map(p -> DashboardResponse.ProductStockAlert.builder()
                        .productId(p.getId())
                        .productName(p.getProductName())
                        .currentStock(p.getQuantity())
                        .threshold(p.getLowStockThreshold())
                        .build())
                .collect(Collectors.toList());

        return DashboardResponse.builder()
                .totalSales(totalSales)
                .totalPurchases(totalPurchases)
                .profit(profit)
                .lowStockItemCount(lowStockProducts.size())
                .branchWiseSales(branchWiseSales)
                .lowStockItems(alerts)
                .build();
    }

    public List<Sale> getSalesReport(LocalDate from, LocalDate to) {
        return saleRepository.findBySaleDateBetween(from, to);
    }

    public List<Purchase> getPurchaseReport(LocalDate from, LocalDate to) {
        return purchaseRepository.findByPurchaseDateBetween(from, to);
    }
}
