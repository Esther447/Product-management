package com.example.productmanagement.repository;

import com.example.productmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_NameIgnoreCase(String categoryName);
    List<Product> findByPriceLessThan(double price);

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%',:q,'%')) OR lower(p.description) LIKE lower(concat('%',:q,'%'))")
    List<Product> searchByNameOrDescription(@Param("q") String query);
}
