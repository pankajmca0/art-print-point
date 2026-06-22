package com.artprintpoint.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseRequest {
    @NotNull
    private Long vendorId;
    private String invoiceNumber;
    private LocalDate purchaseDate;
    private Long branchId;
    private String paymentStatus;
    private String notes;
    @NotNull
    private List<PurchaseItemRequest> items;

    @Data
    public static class PurchaseItemRequest {
        @NotNull
        private Long productId;
        @NotNull
        private Integer quantity;
        @NotNull
        private BigDecimal purchasePrice;
    }
}
