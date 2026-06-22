package com.artprintpoint.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank
    private String name;
    private String phone;
    private String email;
    private String address;
}
