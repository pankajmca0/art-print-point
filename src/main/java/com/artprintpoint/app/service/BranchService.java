package com.artprintpoint.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artprintpoint.app.dto.BranchRequest;
import com.artprintpoint.app.entities.Branch;
import com.artprintpoint.app.repo.BranchRepository;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch create(BranchRequest request) {
        Branch branch = new Branch();
        branch.setBranchName(request.getBranchName());
        branch.setAddress(request.getAddress());
        branch.setContactNumber(request.getContactNumber());
        return branchRepository.save(branch);
    }

    public List<Branch> getAll() {
        return branchRepository.findAll();
    }

    public Branch getById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }

    public Branch update(Long id, BranchRequest request) {
        Branch branch = getById(id);
        branch.setBranchName(request.getBranchName());
        branch.setAddress(request.getAddress());
        branch.setContactNumber(request.getContactNumber());
        return branchRepository.save(branch);
    }

    public void delete(Long id) {
        Branch branch = getById(id);
        branch.setIsActive(false);
        branchRepository.save(branch);
    }
}
