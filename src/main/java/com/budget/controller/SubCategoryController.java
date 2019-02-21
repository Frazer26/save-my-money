package com.budget.controller;

import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class SubCategoryController {

    private SubCategoryService subCategoryService;
    private MainCategoryService mainCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService, MainCategoryService mainCategoryService) {
        this.subCategoryService = subCategoryService;
        this.mainCategoryService = mainCategoryService;
    }

    @PostMapping(value = "/budget/mainCategory/{mainId}/saveSubCategory")
    public ResponseEntity<Object> saveSubCategory(@PathVariable(value = "mainId") Long mainId,
                                       @RequestBody SubCategory subCategoryFromRequest) {
        ResponseEntity responseEntity;
        Optional<MainCategory> mainCategoryOptional = mainCategoryService.getMainCategoryById(mainId);
        if(mainCategoryOptional.isPresent()) {
            MainCategory mainCat = mainCategoryOptional.get();
            subCategoryFromRequest.setMainCategory(mainCat);
            SubCategory subCategory = subCategoryService.addSubCategory(subCategoryFromRequest);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(subCategory.getId()).toUri();

            responseEntity = ResponseEntity.created(location).build();
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/budget/deleteSubCategory/{subCategoryId}")
    public void deleteSubCategory(@PathVariable(value = "subCategoryId") Long subId) {
        subCategoryService.deleteSubCategory(subId);
    }

    @PutMapping(value = "budget/updateSubCategory/{subCategoryId}")
    public ResponseEntity updateSubCategory(@PathVariable(value = "subCategoryId") Long id,
                                            @RequestBody SubCategory subCategoryFromRequest) {
        ResponseEntity responseEntity;
        Optional<SubCategory> subCategoryFromDB = subCategoryService.getSubCategoryById(id);

        if(!subCategoryFromDB.isPresent()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            subCategoryFromRequest.setId(id);
            subCategoryService.addSubCategory(subCategoryFromRequest);
            responseEntity = ResponseEntity.ok().build();
        }
        return responseEntity;
    }
}
