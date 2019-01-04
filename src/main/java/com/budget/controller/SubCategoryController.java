package com.budget.controller;

import com.budget.model.SubCategory;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @RequestMapping(value = "/budget/saveSubCategory", method = RequestMethod.POST)
    public SubCategory newSubCategory(@RequestBody SubCategory subCategory) {
        return subCategoryService.addSubCategory(subCategory);
    }

    @RequestMapping(value = "/budget/deleteSubCategory", method = RequestMethod.POST)
    public void deleteMainCategory(@RequestBody SubCategory subCategory) {
        subCategoryService.deleteSubCategory(subCategory);
    }
}
