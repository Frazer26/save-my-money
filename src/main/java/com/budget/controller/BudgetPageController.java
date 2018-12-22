package com.budget.controller;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@RestController
public class BudgetPageController {

    @RequestMapping(value = "/budget", method = RequestMethod.GET)
    public String index() {
        return getCurrentMonth() + " " + displayCategoriesWithDummyData();
    }

    public String getCurrentMonth() {
        YearMonth thisMonth = YearMonth.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy MMMM");
        return thisMonth.format(monthYearFormatter);
    }

    public String displayCategoriesWithDummyData() {
        LocalDate currentDate = LocalDate.now();
        MainCategory income = new MainCategory("Income",200000);
        SubCategory company = new SubCategory("Epam", 200000, income);
        Item companyMonthlySalary = new Item
                .ItemBuilder("Epam monthly salary", 180000)
                .attachDate(currentDate)
                .belongsToSubCategory(company)
                .build();
        Item cafeteria = new Item
                .ItemBuilder("Cafeteria", 20000)
                .attachDate(currentDate)
                .belongsToSubCategory(company)
                .build();
        String display = income.toString() + " " + company.toString() + " " + companyMonthlySalary.toString() + " " +
                cafeteria.toString();
        return display;
    }

}
