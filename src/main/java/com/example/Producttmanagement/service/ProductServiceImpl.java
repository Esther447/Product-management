package com.example.Producttmanagement.service;

import com.example.productmanagement.dto.ProductDto;
import com.example.productmanagement.exception.ProductNotFoundException;
import com.example.productmanagement.model.Category;
import com.example.productmanagement.model.Product;
import com.example.productmanagement.repository.CategoryRepository;
import com.example.productmanagement.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepo,
                              CategoryRepository categoryRepo,
                              ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Product create(ProductDto dto) {
        Product product = modelMapper.map(dto, Product.class);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id " + dto.getCategoryId()));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        // ensure stock default
        if (product.getStock() == null) {
            product.setStock(0);
        }

        return productRepo.save(product);
    }

    @Transactional
    public Product update(Long id, ProductDto dto) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));

        // map fields from DTO to entity (preserve other fields)
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getPrice() != null) existing.setPrice(dto.getPrice());
        if (dto.getStock() != null) existing.setStock(dto.getStock());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id " + dto.getCategoryId()));
            existing.setCategory(category);
        }

        return productRepo.save(existing);
    }

    public Optional<Product> getById(Long id) {
        return productRepo.findById(id);
    }

    public List<Product> listAll() {
        return productRepo.findAll();
    }

    public Page<Product> list(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public List<Product> search(String query) {
        return productRepo.searchByNameOrDescription(query);
    }

    public void delete(Long id) {
        if (!productRepo.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        productRepo.deleteById(id);
    }

    public List<Product> findByCategoryName(String categoryName) {
        return productRepo.findByCategory_NameIgnoreCase(categoryName);
    }

    public List<Product> findByPriceLessThan(double price) {
        return productRepo.findByPriceLessThan(price);
    }
}
