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

    private MainCategoryService mainCategoryService;

    @Autowired
    public MainCategoryController(MainCategoryService mainCategoryService) {
        this.mainCategoryService = mainCategoryService;
    }

    @PostMapping(value = "/budget/saveMainCategory")
    public ResponseEntity<Object> saveMainCategory(@RequestBody MainCategory mainCategoryFromRequest) {
        ResponseEntity responseEntity;
        MainCategory mainCategory = mainCategoryService.addMainCategory(mainCategoryFromRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(mainCategory.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/budget/deleteMainCategory/{mainCategoryId}")
    public void deleteMainCategory(@PathVariable(value = "mainCategoryId") Long mainCategoryId) {
        mainCategoryService.deleteMainCategory(mainCategoryId);
    }

    @PutMapping(value = "budget/updateMainCategory/{mainCategoryId}")
    public ResponseEntity updateMainCategory(@PathVariable(value = "mainCategoryId") Long id,
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
