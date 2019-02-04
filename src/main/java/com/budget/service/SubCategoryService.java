package com.budget.service;

import com.budget.model.Item;
import com.budget.model.SubCategory;
import com.budget.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    private SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public Optional<SubCategory> getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id);
    }

    public SubCategory addSubCategory(SubCategory subCategory) {
        return subCategoryRepository.saveAndFlush(subCategory);
    }

    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteSubCategoryById(id);
    }

    public int countMoneyUnderSubCat(SubCategory subCategory) {
        List<Item> items = subCategory.getItems();
        return items.stream()
                .mapToInt(Item::getMoney)
                .sum();
    }
}
