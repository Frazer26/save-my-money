package com.budget.repository;

import com.budget.model.MainCategory;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called mainCategoryRepository
// CRUD refers Create, Read, Update, Delete

public interface MainCategoryRepository extends CrudRepository<MainCategory, Long> {
}