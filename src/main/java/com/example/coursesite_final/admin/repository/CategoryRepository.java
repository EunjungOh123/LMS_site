package com.example.coursesite_final.admin.repository;

import com.example.coursesite_final.admin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);
}
