package com.budget.controllerTest;

import com.budget.controller.SubCategoryController;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertEquals;

public class SubCategoryControllerTest {

    private static final String SUB_CATEGORY_NAME = "Household";
    private static final String MAIN_CATEGORY_NAME = "Bills";

    private SubCategoryService mockeSubCategoryService;
    private MainCategoryService mockedMainCategoryService;
    private SubCategoryController subCategoryController;

    @BeforeMethod
    public void setup() {
        mockeSubCategoryService = createMock(SubCategoryService.class);
        mockedMainCategoryService = createMock(MainCategoryService.class);
        subCategoryController = new SubCategoryController(mockeSubCategoryService, mockedMainCategoryService);
    }

    @Test
    public void testSaveSubCategoryWhenRequestBodyNotEmpty() {
        SubCategory testSubCategory = createSubCategory();

        expect(mockedMainCategoryService.getMainCategoryById((long) 0)).andReturn(Optional.of(new MainCategory()));
        expect(mockeSubCategoryService.addSubCategory(testSubCategory)).andReturn(testSubCategory);
        replay(mockedMainCategoryService, mockeSubCategoryService);

        SubCategory subCategory = subCategoryController.saveSubCategory(anyLong(), testSubCategory);

        assertEquals(testSubCategory, subCategory);

        verify(mockedMainCategoryService, mockedMainCategoryService);
    }

    @Test
    public void testDeleteSubCategoryWhenFoundDeletedSubCategoryInDB() {
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);

        mockeSubCategoryService.deleteSubCategory(anyLong());
        expectLastCall();
        replay(mockedMainCategoryService);

        ResponseEntity responseEntity = subCategoryController.deleteSubCategory(anyLong());

        assertEquals(testResponseEntity,responseEntity);

        verify(mockedMainCategoryService);
    }

    @Test
    public void testUpdateSubCategoryWhenSubCategoryFoundInDB() {
        SubCategory testSubCategory = createSubCategory();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);

        expect(mockeSubCategoryService.getSubCategoryById((long) 0)).andReturn(Optional.of(testSubCategory));
        expect(mockeSubCategoryService.addSubCategory(anyObject())).andReturn(testSubCategory);
        replay(mockeSubCategoryService);

        ResponseEntity responseEntity = subCategoryController.updateSubCategory(anyLong(), testSubCategory);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockeSubCategoryService);
    }

    @Test
    public void testUpdateSubCategoryWhenSubCategoryNotFoundInDB() {
        SubCategory testSubCategory = createSubCategory();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        expect(mockeSubCategoryService.getSubCategoryById((long) 0)).andReturn(Optional.empty());
        replay(mockeSubCategoryService);

        ResponseEntity responseEntity = subCategoryController.updateSubCategory(anyLong(), testSubCategory);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockeSubCategoryService);
    }

    private SubCategory createSubCategory() {
        MainCategory mainCategory = new MainCategory(MAIN_CATEGORY_NAME);
        SubCategory subCategory = new SubCategory(SUB_CATEGORY_NAME, mainCategory);
        return subCategory;
    }
}
