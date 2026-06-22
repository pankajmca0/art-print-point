package com.artprintpoint.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artprintpoint.app.dto.SaleRequest;
import com.artprintpoint.app.entities.*;
import com.artprintpoint.app.repo.*;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final InventoryLogRepository inventoryLogRepository;
    private final UserRepository userRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository,
                       CustomerRepository customerRepository, BranchRepository branchRepository,
                       InventoryLogRepository inventoryLogRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.branchRepository = branchRepository;
        this.inventoryLogRepository = inventoryLogRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Sale create(SaleRequest request, String userEmail) {
        Sale sale = new Sale();

        if (request.getCustomerId() != null) {
            sale.setCustomer(customerRepository.findById(request.getCustomerId()).orElse(null));
        }
        if (request.getBranchId() != null) {
            sale.setBranch(branchRepository.findById(request.getBranchId()).orElse(null));
        }

        sale.setDiscount(request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO);
        sale.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CASH");

        User user = userRepository.findByEmail(userEmail).orElse(null);
        sale.setCreatedBy(user);

        List<SaleItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleRequest.SaleItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.getProductId()));

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName()
                        + ". Available: " + product.getQuantity());
            }

            BigDecimal price = itemReq.getSellingPrice() != null ? itemReq.getSellingPrice() : product.getSellingPrice();

            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setSellingPrice(price);
            item.setTotal(price.multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            items.add(item);

            totalAmount = totalAmount.add(item.getTotal());

            // Auto stock deduction
            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            // Inventory log
            InventoryLog log = new InventoryLog();
            log.setProduct(product);
            log.setBranch(sale.getBranch());
            log.setMovementType("OUT");
            log.setQuantity(itemReq.getQuantity());
            log.setReferenceType("SALE");
            inventoryLogRepository.save(log);
        }

        sale.setItems(items);
        sale.setTotalAmount(totalAmount.subtract(sale.getDiscount()));

        Sale saved = saleRepository.save(sale);

        // Update inventory log reference IDs
        for (SaleItem item : saved.getItems()) {
            inventoryLogRepository.findByProductId(item.getProduct().getId())
                    .stream()
                    .filter(l -> l.getReferenceId() == null && "SALE".equals(l.getReferenceType()))
                    .forEach(l -> {
                        l.setReferenceId(saved.getId());
                        inventoryLogRepository.save(l);
                    });
        }

        return saved;
    }

    public List<Sale> getAll() {
        return saleRepository.findAll();
    }

    public Sale getById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    public List<Sale> getByCustomer(Long customerId) {
        return saleRepository.findByCustomerId(customerId);
    }

    public List<Sale> getByBranch(Long branchId) {
        return saleRepository.findByBranchId(branchId);
    }

    @Transactional
    public void reverseSale(Long saleId) {
        Sale sale = getById(saleId);
        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);

            InventoryLog log = new InventoryLog();
            log.setProduct(product);
            log.setBranch(sale.getBranch());
            log.setMovementType("IN");
            log.setQuantity(item.getQuantity());
            log.setReferenceType("SALE_REVERSAL");
            log.setReferenceId(saleId);
            inventoryLogRepository.save(log);
        }
        sale.setPaymentStatus("REVERSED");
        saleRepository.save(sale);
    }
}
