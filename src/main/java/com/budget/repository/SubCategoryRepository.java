package com.budget.repository;

import com.budget.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    void deleteSubCategoryById(Long id);
}
