package com.artprintpoint.app.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artprintpoint.app.dto.ApiResponse;
import com.artprintpoint.app.dto.LoginRequest;
import com.artprintpoint.app.dto.SignupRequest;
import com.artprintpoint.app.service.AuthService;
import com.artprintpoint.app.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<String>> signup(@RequestBody @Valid SignupRequest request) {

		String result = authService.signup(request);

		return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, result, null, LocalDateTime.now(), null));

	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {

		String token = authService.login(request);

		return ResponseEntity.ok(
				new ApiResponse<>(AppConstants.SUCCESS, AppConstants.LOGIN_SUCCESS, token, LocalDateTime.now(), null));

	}

	@PostMapping("/logout/{userId}")
	public ResponseEntity<ApiResponse<String>> logout(@PathVariable("userId") Long userId) {

		String result = authService.logout(userId);

		return ResponseEntity.ok(new ApiResponse<>(AppConstants.SUCCESS, result, null, LocalDateTime.now(), null));

	}

}
