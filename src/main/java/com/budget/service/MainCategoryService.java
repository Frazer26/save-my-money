package com.budget.service;

import com.budget.model.MainCategory;
import com.budget.repository.MainCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainCategoryService {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    public Optional<MainCategory> getMainCategoryById(Long id) {
        return mainCategoryRepository.findById(id);
    }

    public MainCategory createMainCategory(MainCategory mainCategory) {
        return mainCategoryRepository.save(mainCategory);
    }

    public void deleteMainCategory(Long id) {
        mainCategoryRepository.deleteById(id);
    }

}
