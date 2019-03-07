package com.budget.controller;

import com.budget.service.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Controller
public class BudgetPageController {

    private static final String BUDGET = "/budget";
    private static final String MAIN_CATEGORIES = "mainCategories";
    private static final String BUDGET1 = "budget";
    private static final String YYYY_MM = "yyyy MM";
    private final MainCategoryService mainCategoryService;

    @Autowired
    public BudgetPageController(MainCategoryService mainCategoryService) {
        this.mainCategoryService = mainCategoryService;
    }

    @GetMapping(value = BUDGET)
    public String budgetView(Model model) {
        model.addAttribute(MAIN_CATEGORIES, mainCategoryService.getMainCategoryList());
        return BUDGET1;
    }

    public String getCurrentMonth() {
        YearMonth thisMonth = YearMonth.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern(YYYY_MM);
        return thisMonth.format(monthYearFormatter);
    }

}
