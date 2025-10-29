package com.example.productmanagement.service;

import com.example.productmanagement.Dto.ProductDto;
import com.example.productmanagement.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product create(ProductDto dto);
    Product update(Long id, ProductDto dto);
    Optional<Product> getById(Long id);
    List<Product> listAll();
    Page<Product> list(Pageable pageable);
    void delete(Long id);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProduct(java.lang.Long id);
}
