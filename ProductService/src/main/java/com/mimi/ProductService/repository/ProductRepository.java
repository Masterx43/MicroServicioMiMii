package com.mimi.ProductService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mimi.ProductService.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
