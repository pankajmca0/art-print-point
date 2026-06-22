package com.artprintpoint.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artprintpoint.app.dto.PurchaseRequest;
import com.artprintpoint.app.entities.*;
import com.artprintpoint.app.repo.*;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final BranchRepository branchRepository;
    private final InventoryLogRepository inventoryLogRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository,
                           VendorRepository vendorRepository, BranchRepository branchRepository,
                           InventoryLogRepository inventoryLogRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
        this.branchRepository = branchRepository;
        this.inventoryLogRepository = inventoryLogRepository;
    }

    @Transactional
    public Purchase create(PurchaseRequest request) {
        Purchase purchase = new Purchase();
        purchase.setVendor(vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found")));
        purchase.setInvoiceNumber(request.getInvoiceNumber());
        purchase.setPurchaseDate(request.getPurchaseDate());
        purchase.setPaymentStatus(request.getPaymentStatus());
        purchase.setNotes(request.getNotes());

        if (request.getBranchId() != null) {
            purchase.setBranch(branchRepository.findById(request.getBranchId()).orElse(null));
        }

        List<PurchaseItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PurchaseRequest.PurchaseItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.getProductId()));

            PurchaseItem item = new PurchaseItem();
            item.setPurchase(purchase);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPurchasePrice(itemReq.getPurchasePrice());
            item.setTotal(itemReq.getPurchasePrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            items.add(item);

            totalAmount = totalAmount.add(item.getTotal());

            // Auto stock increase
            product.setQuantity(product.getQuantity() + itemReq.getQuantity());
            productRepository.save(product);

            // Inventory log
            InventoryLog log = new InventoryLog();
            log.setProduct(product);
            log.setBranch(purchase.getBranch());
            log.setMovementType("IN");
            log.setQuantity(itemReq.getQuantity());
            log.setReferenceType("PURCHASE");
            inventoryLogRepository.save(log);
        }

        purchase.setItems(items);
        purchase.setTotalAmount(totalAmount);

        Purchase saved = purchaseRepository.save(purchase);

        // Update inventory log reference IDs
        inventoryLogRepository.findByProductId(saved.getItems().get(0).getProduct().getId())
                .stream()
                .filter(l -> l.getReferenceId() == null && "PURCHASE".equals(l.getReferenceType()))
                .forEach(l -> {
                    l.setReferenceId(saved.getId());
                    inventoryLogRepository.save(l);
                });

        return saved;
    }

    public List<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    public Purchase getById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public List<Purchase> getByVendor(Long vendorId) {
        return purchaseRepository.findByVendorId(vendorId);
    }

    public List<Purchase> getByBranch(Long branchId) {
        return purchaseRepository.findByBranchId(branchId);
    }
}
