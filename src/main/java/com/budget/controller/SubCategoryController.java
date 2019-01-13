package com.budget.controller;

import com.budget.model.SubCategory;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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
    public ResponseEntity<?> deleteSubCategory(@PathVariable(value = "mainId") Long mainId,
                                             @PathVariable(value = "subCategoryId") Long subId) throws Exception {
        return subCategoryService.getSubCategoryByIdAndMainCategoryId(subId, mainId).map(subCategory -> {
            subCategoryService.deleteSubCategory(subCategory);
            return ResponseEntity.ok().build();
        }).orElseThrow(Exception::new);
    }
}
