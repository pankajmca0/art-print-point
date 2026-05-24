package com.artprintpoint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artprintpoint.app.entities.UserAuditLog;

public interface UserAuditRepository extends JpaRepository<UserAuditLog, Long> {
}
