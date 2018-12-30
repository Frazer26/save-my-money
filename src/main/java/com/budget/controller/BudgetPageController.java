package com.budget.controller;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.repository.ItemRepository;
import com.budget.repository.MainCategoryRepository;
import com.budget.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@RestController
public class BudgetPageController {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/budget", method = RequestMethod.GET)
    public String index() {
        MainCategory testCategory = new MainCategory("TestMainCategory");
        mainCategoryRepository.save(testCategory);

        SubCategory subCategory = new SubCategory("TestSubCategory", testCategory);
        subCategoryRepository.save(subCategory);

        Item item = new Item.ItemBuilder("TestElectricity",2000)
                .belongsToSubCategory(subCategory)
                .build();
        itemRepository.save(item);

        return "Saved test data";
    }

    @RequestMapping(value = "/all")
    public Iterable<MainCategory> getAllMainCategories() {
        return mainCategoryRepository.findAll();
    }

    public String getCurrentMonth() {
        YearMonth thisMonth = YearMonth.now();
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy MMMM");
        return thisMonth.format(monthYearFormatter);
    }

    public String displayCategoriesWithDummyData() {
        Date currentDate = new Date(2);
        MainCategory income = new MainCategory("Income");
        SubCategory company = new SubCategory("Epam", income);
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
