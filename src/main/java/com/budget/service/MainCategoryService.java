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

    private MainCategoryRepository mainCategoryRepository;

    @Autowired
    public MainCategoryService(MainCategoryRepository mainCategoryRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
    }

    public Optional<MainCategory> getMainCategoryById(Long id) {
        return mainCategoryRepository.findById(id);
    }

    public MainCategory addMainCategory(MainCategory mainCategory) {
        return mainCategoryRepository.saveAndFlush(mainCategory);
    }

    public void deleteMainCategory(MainCategory mainCategory) {
        mainCategoryRepository.delete(mainCategory);
    }

    public List<MainCategory> mainCategoryList() {
        return mainCategoryRepository.findAll();
    }

    public int countMoneyUnderMainCat(MainCategory mainCategory) {
        List<SubCategory> subcategories = mainCategory.getSubCategories();
        List<Item> items = mainCategory.getItems();

        int money = countMoneyIfSubCatEmpty(items);

        if (!subcategories.isEmpty()) {
            money = countMoneyIfSubCatNotEmpty(subcategories);
        }
        return money;
    }

    private int countMoneyIfSubCatEmpty(List<Item> items) {
        return items.stream()
                .mapToInt(Item::getMoney)
                .sum();
    }

    private int countMoneyIfSubCatNotEmpty(List<SubCategory> subcategories) {
        return subcategories.stream()
                .flatMap(subCat -> subCat.getItems().stream())
                .mapToInt(Item::getMoney)
                .sum();
    }

}
