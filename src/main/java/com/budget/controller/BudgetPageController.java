package com.budget.controller;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.service.ItemService;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;
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

    @RequestMapping(value = "/budget", method = RequestMethod.GET)
    public String budgetView(Model model) {
        model.addAttribute("mainCategories", mainCategoryService.mainCategoryList());
        return "budget";
    }

    public String getCurrentMonth() {
        YearMonth thisMonth = YearMonth.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy MMMM");
        return thisMonth.format(monthYearFormatter);
    }

    @RequestMapping(value = "/saveData", method = RequestMethod.GET)
    public void saveCategoriesWithDummyData() {
        Date currentDate = new Date(2018, 12, 30);

        MainCategory income = new MainCategory("Income");
        mainCategoryService.addMainCategory(income);

        SubCategory company = new SubCategory("Epam", income);
        subCategoryService.addSubCategory(company);

        Item companyMonthlySalary = new Item
                .ItemBuilder("Epam monthly salary", 180000)
                .attachDate(currentDate)
                .belongsToSubCategory(company)
                .build();
        itemService.addItem(companyMonthlySalary);

        Item cafeteria = new Item
                .ItemBuilder("Cafeteria", 20000)
                .attachDate(currentDate)
                .belongsToSubCategory(company)
                .build();
        itemService.addItem(cafeteria);

        SubCategory pocketMoney = new SubCategory("Pocket Money", income);
        subCategoryService.addSubCategory(pocketMoney);

        Item grandpa = new Item
                .ItemBuilder("Money from grandpa", 5000)
                .attachDate(currentDate)
                .belongsToSubCategory(pocketMoney)
                .build();
        itemService.addItem(grandpa);

    }

}
