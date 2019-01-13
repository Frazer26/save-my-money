package com.budget.controller;

import com.budget.model.MainCategory;
import com.budget.service.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainCategoryController {

    private MainCategoryService mainCategoryService;

    @Autowired
    public MainCategoryController(MainCategoryService mainCategoryService) {
        this.mainCategoryService = mainCategoryService;
    }

    @PostMapping(value = "/budget/saveMainCategory")
    public MainCategory saveMainCategory(@RequestBody MainCategory mainCategory) {
        return mainCategoryService.addMainCategory(mainCategory);
    }

    @DeleteMapping(value = "/budget/deleteMainCategory/{mainCategoryId}")
    public ResponseEntity<?> deleteMainCategory(@PathVariable (value = "mainCategoryId") Long mainCategoryId) throws Exception {
        return mainCategoryService.getMainCategoryById(mainCategoryId).map(mainCategory -> {
            mainCategoryService.deleteMainCategory(mainCategory);
            return ResponseEntity.ok().build();
        }).orElseThrow(Exception::new);

    }
}
