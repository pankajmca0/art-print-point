package com.artprintpoint.app.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.artprintpoint.app.dto.LoginRequest;
import com.artprintpoint.app.dto.SignupRequest;
import com.artprintpoint.app.entities.User;
import com.artprintpoint.app.entities.UserAuditLog;
import com.artprintpoint.app.repo.UserAuditRepository;
import com.artprintpoint.app.repo.UserRepository;
import com.artprintpoint.app.utils.AppConstants;
import com.artprintpoint.app.utils.JwtUtil;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final UserAuditRepository auditRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;


	public AuthService(UserRepository userRepository, UserAuditRepository auditRepository,
			PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.auditRepository = auditRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	public String signup(SignupRequest request) {

		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setContactNumber(request.getContactNumber());
		user.setAddress(request.getAddress());
		user.setRole(AppConstants.ROLE_USER);
		user.setIsActive(true);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		userRepository.save(user);
		return AppConstants.USER_CREATED;
	}

	public String login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException(AppConstants.USER_NOT_FOUND));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException(AppConstants.AUTH_INVALID);
		}

		// ✅ audit log
		UserAuditLog audit = new UserAuditLog();
		audit.setUserId(user.getId());
		audit.setAction("LOGIN");
		audit.setLoginTime(LocalDateTime.now());
		auditRepository.save(audit);

		// ✅ return token
		return jwtUtil.generateToken(user.getEmail());

	}

	public String logout(Long userId) {

		UserAuditLog audit = new UserAuditLog();
		audit.setUserId(userId);
		audit.setAction("LOGOUT");
		audit.setLogoutTime(LocalDateTime.now());

		auditRepository.save(audit);

		return AppConstants.LOGOUT_SUCCESS;
	}

}
