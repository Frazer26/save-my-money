package com.budget.controller;

import com.budget.model.SubCategory;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubCategoryController {

    private static final String SUB_CATEGORY_ID = "subCategoryId";
    private SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping(value = "/budget/subcategory")
    public ResponseEntity<List<SubCategory>> getAllSubcategories() {
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }


    @PostMapping(value = "/budget/subcategory")
    public ResponseEntity saveSubCategoryUnderCost(@RequestBody SubCategory subCategory) {
        //validálást betenni?
        SubCategory savedSubCategory = subCategoryService.saveSubCategory(subCategory);
        return new ResponseEntity<>(savedSubCategory, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/budget/deleteSubcategory/{subCategoryId}")
    public void deleteSubCategory(@PathVariable(value = SUB_CATEGORY_ID) Long id) {
        subCategoryService.deleteSubCategory(id);
    }

    @PutMapping(value = "budget/updateSubCategory/{subCategoryId}")
    public ResponseEntity updateSubCategory(@PathVariable(value = SUB_CATEGORY_ID) Long id,
                                            @RequestBody SubCategory subCategory) {
        SubCategory updatedSubCategory = subCategoryService.updateSubCategory(subCategory, id);
        if (updatedSubCategory.equals(SubCategory.EMPTY)) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(updatedSubCategory, HttpStatus.OK);
    }

}
