package com.budget.controller;

import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.service.ItemService;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
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
    public ResponseEntity<Object> saveItemInMainCategory(@PathVariable(value = "mainId") Long mainId,
                                       @RequestBody Item itemFromRequest) {
        ResponseEntity responseEntity;
        Optional<MainCategory> mainCategoryOptional = mainCategoryService.getMainCategoryById(mainId);
        if (mainCategoryOptional.isPresent()) {
            MainCategory mainCat = mainCategoryOptional.get();
            itemFromRequest.setMainCategory(mainCat);
            Item item = itemService.addItem(itemFromRequest);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(item.getId()).toUri();

            responseEntity = ResponseEntity.created(location).build();
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    @PostMapping(value = "/budget/subCategory/{subId}/saveItem")
    public ResponseEntity<Object> saveItemInSubCategory(@PathVariable(value = "subId") Long subId,
                                      @RequestBody Item itemFromRequest) {
        ResponseEntity responseEntity;
        Optional<SubCategory> subCategoryOptional = subCategoryService.getSubCategoryById(subId);
        if (subCategoryOptional.isPresent()) {
            SubCategory subCat = subCategoryOptional.get();
            itemFromRequest.setSubCategory(subCat);
            Item item = itemService.addItem(itemFromRequest);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(item.getId()).toUri();

            responseEntity = ResponseEntity.created(location).build();
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/budget/item/{itemId}")
    public void deleteItem(@PathVariable(value = "itemId") Long itemId) {
        itemService.deleteItem(itemId);
    }

    @PutMapping(value = "budget/updateItem/{itemId}")
    public ResponseEntity updateItem(@PathVariable(value = "itemId") Long id,
                                        @RequestBody Item itemFromRequest) {
        ResponseEntity responseEntity;
        Optional<Item> itemFromDB = itemService.getItemById(id);

        if (!itemFromDB.isPresent()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            itemFromRequest.setId(id);
            itemService.addItem(itemFromRequest);
            responseEntity = ResponseEntity.ok().build();
        }

        return responseEntity;
    }

}
