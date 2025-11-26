package com.mimi.ProductService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mimi.ProductService.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
