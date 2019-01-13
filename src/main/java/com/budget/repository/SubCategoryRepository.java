package com.budget.repository;

import com.budget.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findSubCategoryByIdAndMainCategory_Id(Long id, Long MainCategoryId);

}
