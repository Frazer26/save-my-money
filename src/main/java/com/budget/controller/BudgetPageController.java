package com.budget.controller;

//import com.budget.model.Item;
import com.budget.model.MainCategory;
//import com.budget.model.SubCategory;
import com.budget.repository.MainCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@RestController
public class BudgetPageController {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @RequestMapping(value = "/budget", method = RequestMethod.GET)
    public String index(@RequestParam String name, @RequestParam int income) {
        MainCategory testCategory = new MainCategory(name, income);
        mainCategoryRepository.save(testCategory);
        return "Saved";
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

//    public String displayCategoriesWithDummyData() {
//        LocalDate currentDate = LocalDate.now();
//        MainCategory income = new MainCategory("Income",200000);
//        SubCategory company = new SubCategory("Epam", 200000, income);
//        Item companyMonthlySalary = new Item
//                .ItemBuilder("Epam monthly salary", 180000)
//                .attachDate(currentDate)
//                .belongsToSubCategory(company)
//                .build();
//        Item cafeteria = new Item
//                .ItemBuilder("Cafeteria", 20000)
//                .attachDate(currentDate)
//                .belongsToSubCategory(company)
//                .build();
//        String display = income.toString() + " " + company.toString() + " " + companyMonthlySalary.toString() + " " +
//                cafeteria.toString();
//        return display;
//    }

}
