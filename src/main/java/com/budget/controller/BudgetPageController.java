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

    private final MainCategoryService mainCategoryService;

    @Autowired
    public BudgetPageController(MainCategoryService mainCategoryService) {
        this.mainCategoryService = mainCategoryService;
    }

    @GetMapping(value = "/budget")
    public String budgetView(Model model) {
        model.addAttribute("mainCategories", mainCategoryService.getMainCategoryList());
        return "budget";
    }

    public String getCurrentMonth() {
        YearMonth thisMonth = YearMonth.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy MM");
        return thisMonth.format(monthYearFormatter);
    }

}
