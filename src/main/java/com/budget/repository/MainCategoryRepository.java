package com.budget.repository;

import com.budget.model.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called mainCategoryRepository
// JpaRepository contains the full API of CrudRepository and PagingAndSortingRepository.
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
}