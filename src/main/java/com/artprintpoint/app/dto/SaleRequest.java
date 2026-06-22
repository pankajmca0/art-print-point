package com.artprintpoint.app.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleRequest {
    private Long customerId;
    private Long branchId;
    private BigDecimal discount;
    private String paymentMethod;
    @NotNull
    private List<SaleItemRequest> items;

    @Data
    public static class SaleItemRequest {
        @NotNull
        private Long productId;
        @NotNull
        private Integer quantity;
        private BigDecimal sellingPrice; // optional, defaults to product selling price
    }
}
