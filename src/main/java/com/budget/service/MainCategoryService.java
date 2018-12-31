package com.budget.service;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.repository.MainCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MainCategoryService {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    public Optional<MainCategory> getMainCategoryById(Long id) {
        return mainCategoryRepository.findById(id);
    }

    public MainCategory addMainCategory(MainCategory mainCategory) {
        return mainCategoryRepository.save(mainCategory);
    }

    public void deleteMainCategory(Long id) {
        mainCategoryRepository.deleteById(id);
    }

    public int countMoneyUnderMainCat(MainCategory mainCategory) {
        List<SubCategory> subcategories = mainCategory.getSubCategories();
        List<Item> items = mainCategory.getItems();

        if (!subcategories.isEmpty()) {
            return countMoneyIfSubCatNotEmpty(subcategories);
        }
        return countMoneyIfSubCatEmpty(items);
    }

    private int countMoneyIfSubCatEmpty(List<Item> items) {
        return items.stream()
                .mapToInt(item -> item.getMoney())
                .sum();
    }

    private int countMoneyIfSubCatNotEmpty(List<SubCategory> subcategories) {
        return subcategories.stream()
                .flatMap(subCat -> subCat.getItems().stream())
                .mapToInt(item -> item.getMoney())
                .sum();
    }

}
