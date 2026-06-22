package com.artprintpoint.app.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
    private BigDecimal totalSales;
    private BigDecimal totalPurchases;
    private BigDecimal profit;
    private Integer lowStockItemCount;
    private Map<String, BigDecimal> branchWiseSales;
    private List<ProductStockAlert> lowStockItems;

    @Data
    @Builder
    public static class ProductStockAlert {
        private Long productId;
        private String productName;
        private Integer currentStock;
        private Integer threshold;
    }
}
