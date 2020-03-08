package com.budget.repository;

import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findSubCategoriesByMainCategory(MainCategory mainCategory);

    void deleteSubCategoryById(Long id);

    SubCategory findSubCategoryByName(String subCategoryName);
}
