package com.budget.controller;

import com.budget.model.MainCategory;
import com.budget.service.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
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
    public ResponseEntity<?> deleteMainCategory(@PathVariable(value = "mainCategoryId") Long mainCategoryId) {
        mainCategoryService.deleteMainCategory(mainCategoryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "budget/updateMainCategory/{mainCategoryId}")
    public ResponseEntity<?> updateMainCategory(@PathVariable(value = "mainCategoryId") Long id,
                                                @RequestBody MainCategory mainCategoryFromRequest) {
        ResponseEntity responseEntity;
        Optional<MainCategory> mainCategoryFromDB = mainCategoryService.getMainCategoryById(id);

        if (!mainCategoryFromDB.isPresent()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            mainCategoryFromRequest.setId(id);
            mainCategoryService.addMainCategory(mainCategoryFromRequest);
            responseEntity = ResponseEntity.ok().build();
        }

        return responseEntity;
    }

}
