package com.mimi.ProductService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mimi.ProductService.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
