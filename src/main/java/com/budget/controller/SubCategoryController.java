package com.budget.controller;

import com.budget.model.SubCategory;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public SubCategory saveSubCategory(@PathVariable(value = "mainId") Long mainId,
                                       @RequestBody SubCategory subCategory) throws Exception {
        return mainCategoryService.getMainCategoryById(mainId).map(mainCategory -> {
            subCategory.setMainCategory(mainCategory);
            return subCategoryService.addSubCategory(subCategory);
        }).orElseThrow(Exception::new);
    }

    @DeleteMapping(value = "/budget/mainCategory/{mainId}/subCategory/{subCategoryId}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable(value = "subCategoryId") Long subId) {
        subCategoryService.deleteSubCategory(subId);
        return ResponseEntity.ok().build();
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
