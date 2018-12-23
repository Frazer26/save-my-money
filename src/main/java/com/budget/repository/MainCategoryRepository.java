package com.budget.repository;

import org.springframework.data.repository.CrudRepository;

import com.budget.model.MainCategory;

// This will be AUTO IMPLEMENTED by Spring into a Bean called mainCategoryRepository
// CRUD refers Create, Read, Update, Delete

public interface MainCategoryRepository extends CrudRepository<MainCategory, Integer> {

}