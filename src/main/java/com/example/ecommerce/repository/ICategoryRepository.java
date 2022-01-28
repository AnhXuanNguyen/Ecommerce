package com.example.ecommerce.repository;

import com.example.ecommerce.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
