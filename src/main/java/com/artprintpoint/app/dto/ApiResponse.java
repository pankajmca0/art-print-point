package com.artprintpoint.app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

	private String status;
	private String message;
	private T data;
	private LocalDateTime timestamp;
	private String errorCode;

}
