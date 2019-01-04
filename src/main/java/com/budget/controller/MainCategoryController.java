package com.budget.controller;

import com.budget.model.MainCategory;
import com.budget.service.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainCategoryController {

    @Autowired
    private MainCategoryService mainCategoryService;

    @RequestMapping(value = "/budget/saveMainCategory", method = RequestMethod.POST)
    public MainCategory newMainCategory(@RequestBody MainCategory mainCategory) {
        return mainCategoryService.addMainCategory(mainCategory);
    }

    @RequestMapping(value = "/budget/deleteMainCategory", method = RequestMethod.POST)
    public void deleteMainCategory(@RequestBody MainCategory mainCategory) {
        mainCategoryService.deleteMainCategory(mainCategory);
    }
}
