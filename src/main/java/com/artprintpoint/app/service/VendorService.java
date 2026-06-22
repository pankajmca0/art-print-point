package com.artprintpoint.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artprintpoint.app.dto.VendorRequest;
import com.artprintpoint.app.entities.Vendor;
import com.artprintpoint.app.repo.VendorRepository;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Vendor create(VendorRequest request) {
        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setContactPerson(request.getContactPerson());
        vendor.setPhone(request.getPhone());
        vendor.setEmail(request.getEmail());
        vendor.setAddress(request.getAddress());
        vendor.setGstNumber(request.getGstNumber());
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    public Vendor getById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    public Vendor update(Long id, VendorRequest request) {
        Vendor vendor = getById(id);
        vendor.setName(request.getName());
        vendor.setContactPerson(request.getContactPerson());
        vendor.setPhone(request.getPhone());
        vendor.setEmail(request.getEmail());
        vendor.setAddress(request.getAddress());
        vendor.setGstNumber(request.getGstNumber());
        return vendorRepository.save(vendor);
    }

    public void delete(Long id) {
        Vendor vendor = getById(id);
        vendor.setIsActive(false);
        vendorRepository.save(vendor);
    }
}
