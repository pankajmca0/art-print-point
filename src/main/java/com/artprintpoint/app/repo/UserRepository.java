package com.artprintpoint.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artprintpoint.app.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

