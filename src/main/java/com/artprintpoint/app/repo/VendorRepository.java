package com.artprintpoint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.artprintpoint.app.entities.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
