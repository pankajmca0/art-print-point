package com.artprintpoint.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BranchRequest {
    @NotBlank
    private String branchName;
    private String address;
    private String contactNumber;
}
