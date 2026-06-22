package com.artprintpoint.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.artprintpoint.app.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhone(String phone);
}
