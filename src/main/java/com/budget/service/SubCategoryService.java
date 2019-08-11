package com.budget.service;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.budget.model.SubCategory.EMPTY;

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

    public List<SubCategory> getAllSubCategories(){
        return subCategoryRepository.findAll();
    }

    public SubCategory saveSubCategory(SubCategory subCategory) {
        subCategory.setMainCategory(MainCategory.COST);
        return subCategoryRepository.save(subCategory);
    }

    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteSubCategoryById(id);
    }

    public SubCategory updateSubCategory(SubCategory subCategory, Long id) {
        Optional<SubCategory> subCategoryFromDB = getSubCategoryById(id);

        if (subCategoryFromDB.isEmpty()) {
            return EMPTY;
        }

        subCategory.setId(id);
        return saveSubCategory(subCategory);
    }

    public int countMoneyUnderSubCat(SubCategory subCategory) {
        List<Item> items = subCategory.getItems();
        return items.stream()
                .mapToInt(Item::getMoney)
                .sum();
    }
}
