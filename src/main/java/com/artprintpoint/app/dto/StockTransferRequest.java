package com.artprintpoint.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockTransferRequest {
    @NotNull
    private Long productId;
    @NotNull
    private Long fromBranchId;
    @NotNull
    private Long toBranchId;
    @NotNull
    private Integer quantity;
}
