package com.budget.controller;

import com.budget.model.MainCategory;
import com.budget.service.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class MainCategoryController {

    private static final String SAVE_MAIN_CATEGORY_ENDPOINT = "/budget/saveMainCategory";
    private static final String DELETE_MAIN_CATEGORY_ENDPOINT = "/budget/deleteMainCategory/{mainCategoryId}";
    private static final String UPDATE_MAIN_CATEGORY_ENDPOINT = "budget/updateMainCategory/{mainCategoryId}";
    private static final String MAIN_CATEGORY_ID = "mainCategoryId";
    private static final String ID = "/{id}";
    private MainCategoryService mainCategoryService;

    @Autowired
    public MainCategoryController(MainCategoryService mainCategoryService) {
        this.mainCategoryService = mainCategoryService;
    }

    @PostMapping(value = SAVE_MAIN_CATEGORY_ENDPOINT)
    public ResponseEntity<Object> saveMainCategory(@RequestBody MainCategory mainCategoryFromRequest) {
        MainCategory mainCategory = mainCategoryService.addMainCategory(mainCategoryFromRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(mainCategory.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = DELETE_MAIN_CATEGORY_ENDPOINT)
    public void deleteMainCategory(@PathVariable(value = MAIN_CATEGORY_ID) Long mainCategoryId) {
        mainCategoryService.deleteMainCategory(mainCategoryId);
    }

    @PutMapping(value = UPDATE_MAIN_CATEGORY_ENDPOINT)
    public ResponseEntity updateMainCategory(@PathVariable(value = MAIN_CATEGORY_ID) Long id,
                                             @RequestBody MainCategory mainCategoryFromRequest) {
        ResponseEntity responseEntity;
        Optional<MainCategory> mainCategoryFromDB = mainCategoryService.getMainCategoryById(id);

        if (!mainCategoryFromDB.isPresent()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            mainCategoryService.updateMainCategory(mainCategoryFromRequest, id);
            responseEntity = ResponseEntity.ok().build();
        }

        return responseEntity;
    }

}
