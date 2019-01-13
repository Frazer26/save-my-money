package com.budget.controller;

import com.budget.model.Item;
import com.budget.service.ItemService;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ItemController {

    private ItemService itemService;

    private MainCategoryService mainCategoryService;

    private SubCategoryService subCategoryService;

    @Autowired
    public ItemController(ItemService itemService, MainCategoryService mainCategoryService,
                          SubCategoryService subCategoryService) {
        this.itemService = itemService;
        this.mainCategoryService = mainCategoryService;
        this.subCategoryService = subCategoryService;
    }

    @PostMapping(value = "/budget/mainCategory/{mainId}/saveItem")
    public Item saveItemInMainCategory(@PathVariable(value = "mainId") Long mainId,
                                      @RequestBody Item item) throws Exception {
        return mainCategoryService.getMainCategoryById(mainId).map(mainCategory -> {
            item.setMainCategory(mainCategory);
            return itemService.addItem(item);
        }).orElseThrow(Exception::new);
    }

    @PostMapping(value = "/budget/subCategory/{subId}/saveItem")
    public Item saveItemInSubCategory(@PathVariable(value = "subId") Long subId,
                                      @RequestBody Item item) throws Exception {
        return subCategoryService.getSubCategoryById(subId).map(subCategory -> {
            item.setSubCategory(subCategory);
            return itemService.addItem(item);
        }).orElseThrow(Exception::new);
    }

    @PostMapping(value = "/budget/mainCategory/{mainId}/item/{itemId}")
    public ResponseEntity<?> deleteItemFromMainCategory(@PathVariable (value = "mainId") Long mainId,
                                     @PathVariable (value = "itemId") Long itemId) throws Exception {
        return itemService.getItemByIdAndMainCategoryId(itemId,mainId).map(item -> {
            itemService.deleteItem(item);
            return ResponseEntity.ok().build();
        }).orElseThrow(Exception::new);
    }

    @PostMapping(value = "/budget/subCategory/{subId}/item/{itemId}")
    public ResponseEntity<?> deleteItemFromSubCategory(@PathVariable (value = "subId") Long subId,
                                     @PathVariable (value = "itemId") Long itemId) throws Exception {
        return itemService.getItemByIdAndSubCategoryId(itemId,subId).map(item -> {
            itemService.deleteItem(item);
            return ResponseEntity.ok().build();
        }).orElseThrow(Exception::new);
    }
}
