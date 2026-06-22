package com.artprintpoint.app.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank
    private String productName;
    @NotNull
    private Long categoryId;
    private Long vendorId;
    private Long branchId;
    @NotNull
    private BigDecimal purchasePrice;
    @NotNull
    private BigDecimal sellingPrice;
    private Integer quantity;
    private String unit;
    private String barcode;
    private String designModelNumber;
    private String description;
    private String productImage;
    private Integer lowStockThreshold;
}
