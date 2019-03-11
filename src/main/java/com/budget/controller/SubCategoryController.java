package com.budget.controller;

import com.budget.model.SubCategory;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
public class SubCategoryController {

    private static final String SAVE_SUB_CATEGORY_ENDPOINT = "/budget/mainCategory/{mainId}/saveSubCategoryInMainCategory";
    private static final String DELETE_SUB_CATEGORY_ENDPOINT = "/budget/deleteSubCategory/{subCategoryId}";
    private static final String UPDATE_MAIN_CATEGORY_ENDPOINT = "budget/updateSubCategory/{subCategoryId}";
    private static final String SUB_CATEGORY_ID = "subCategoryId";
    private static final String ID = "/{id}";
    private static final String MAIN_CATEGORY_ID = "mainId";
    private SubCategoryService subCategoryService;
    private MainCategoryService mainCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService, MainCategoryService mainCategoryService) {
        this.subCategoryService = subCategoryService;
        this.mainCategoryService = mainCategoryService;
    }

    @PostMapping(value = SAVE_SUB_CATEGORY_ENDPOINT)
    public ResponseEntity<Object> saveSubCategoryInMainCategory(@PathVariable(value = MAIN_CATEGORY_ID) Long mainId,
                                                                @RequestBody SubCategory subCategoryFromRequest) {
        return mainCategoryService.getMainCategoryById(mainId)
                .map(mainCategory -> subCategoryService.addSubCategoryUnderMainCategory(subCategoryFromRequest, mainCategory))
                .map(item -> ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(item.getId()).toUri())
                .map(uri -> ResponseEntity.created(uri).build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = DELETE_SUB_CATEGORY_ENDPOINT)
    public void deleteSubCategory(@PathVariable(value = SUB_CATEGORY_ID) Long subId) {
        subCategoryService.deleteSubCategory(subId);
    }

    @PutMapping(value = UPDATE_MAIN_CATEGORY_ENDPOINT)
    public ResponseEntity updateSubCategory(@PathVariable(value = SUB_CATEGORY_ID) Long id,
                                            @RequestBody SubCategory subCategoryFromRequest) {
        ResponseEntity responseEntity;
        Optional<SubCategory> subCategoryFromDB = subCategoryService.getSubCategoryById(id);

        if (!subCategoryFromDB.isPresent()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            subCategoryService.addSubCategory(subCategoryFromRequest, id);
            responseEntity = ResponseEntity.ok().build();
        }
        return responseEntity;
    }
}
