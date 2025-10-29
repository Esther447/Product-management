package com.example.productmanagement.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String name;
    private BigDecimal price;
    private String description;
    private Integer stock;
    private Long categoryId;


    // getters + setters
}
