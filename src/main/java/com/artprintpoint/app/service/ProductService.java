package com.artprintpoint.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.artprintpoint.app.dto.ProductRequest;
import com.artprintpoint.app.entities.Product;
import com.artprintpoint.app.repo.BranchRepository;
import com.artprintpoint.app.repo.CategoryRepository;
import com.artprintpoint.app.repo.ProductRepository;
import com.artprintpoint.app.repo.VendorRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;
    private final BranchRepository branchRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          VendorRepository vendorRepository, BranchRepository branchRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
        this.branchRepository = branchRepository;
    }

    public Product create(ProductRequest request) {
        Product product = new Product();
        mapRequestToEntity(request, product);
        if (product.getBarcode() == null || product.getBarcode().isBlank()) {
            product.setBarcode(generateBarcode());
        }
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product getByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Product not found for barcode: " + barcode));
    }

    public List<Product> getByBranch(Long branchId) {
        return productRepository.findByBranchId(branchId);
    }

    public List<Product> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    public List<Product> search(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    public Product update(Long id, ProductRequest request) {
        Product product = getById(id);
        mapRequestToEntity(request, product);
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = getById(id);
        product.setStatus("INACTIVE");
        productRepository.save(product);
    }

    private void mapRequestToEntity(ProductRequest request, Product product) {
        product.setProductName(request.getProductName());
        product.setCategory(categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));
        if (request.getVendorId() != null) {
            product.setVendor(vendorRepository.findById(request.getVendorId()).orElse(null));
        }
        if (request.getBranchId() != null) {
            product.setBranch(branchRepository.findById(request.getBranchId()).orElse(null));
        }
        product.setPurchasePrice(request.getPurchasePrice());
        product.setSellingPrice(request.getSellingPrice());
        product.setQuantity(request.getQuantity() != null ? request.getQuantity() : 0);
        product.setUnit(request.getUnit() != null ? request.getUnit() : "piece");
        product.setBarcode(request.getBarcode());
        product.setDesignModelNumber(request.getDesignModelNumber());
        product.setDescription(request.getDescription());
        product.setProductImage(request.getProductImage());
        if (request.getLowStockThreshold() != null) {
            product.setLowStockThreshold(request.getLowStockThreshold());
        }
    }

    private String generateBarcode() {
        return "ART-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
