package com.budget.controller;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.budget.model.Item.EMPTY;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ItemController {

    private static final String ID = "id";
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/budget/{mainCategory}")
    public List<Item> getItemUnderMainCategory(@PathVariable("mainCategory") MainCategory mainCategory,
                                               @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                               @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return itemService.getItemsUnderMainCategory(mainCategory, startDate, endDate);
    }

    @GetMapping(value = "/budget/subcategory")
    public List<Item> getAllItemsWhereSubCategoryNotNull() {
        return itemService.getAllItemsWhereSubCategoryNotNull();
    }

    @PostMapping(value = "/budget/{mainCategory}")
    public ResponseEntity saveItemUnderMainCategory(@PathVariable MainCategory mainCategory, @RequestBody Item item) {
        Item savedItem = itemService.saveItemUnderMainCategory(item, mainCategory);
        if (savedItem.equals(EMPTY)) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PostMapping(value = "/budget/subcategory/{subcategory}")
    public ResponseEntity saveItemUnderSubCategory(@PathVariable String subcategory, @RequestBody Item item) {
        Item savedItem = itemService.saveItemUnderSubCategory(item, subcategory);
        if (savedItem.equals(EMPTY)) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/budget/deleteItem/{id}")
    public void deleteItem(@PathVariable(value = ID) Long id) {
        itemService.deleteItem(id);
    }

    @PutMapping(value = "budget/updateItem/{id}")
    public ResponseEntity updateItem(@PathVariable(value = ID) Long id,
                                     @RequestBody Item item) {
        Item updatedItem = itemService.updateItem(item, id);
        if (updatedItem.equals(EMPTY)) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}
