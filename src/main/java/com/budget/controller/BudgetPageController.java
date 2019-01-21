package com.budget.controller;

import com.budget.service.ItemService;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Controller
public class BudgetPageController {

    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/budget")
    public String budgetView(Model model) {
        model.addAttribute("mainCategories", mainCategoryService.mainCategoryList());
        return "budget";
    }

    public String getCurrentMonth() {
        YearMonth thisMonth = YearMonth.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy MMMM");
        return thisMonth.format(monthYearFormatter);
    }

}
