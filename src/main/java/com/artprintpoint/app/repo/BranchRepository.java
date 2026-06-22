package com.artprintpoint.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.artprintpoint.app.entities.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
