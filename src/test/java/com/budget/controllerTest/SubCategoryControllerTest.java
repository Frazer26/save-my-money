package com.budget.controllerTest;

import com.budget.controller.SubCategoryController;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.service.MainCategoryService;
import com.budget.service.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertEquals;

public class SubCategoryControllerTest {

    private static final String SUB_CATEGORY_NAME = "Household";
    private static final String MAIN_CATEGORY_NAME = "Bills";

    private SubCategoryService mockedSubCategoryService;
    private MainCategoryService mockedMainCategoryService;
    private SubCategoryController subCategoryController;
    private MockHttpServletRequest request;


    @BeforeMethod
    public void setup() {
        mockedSubCategoryService = createMock(SubCategoryService.class);
        mockedMainCategoryService = createMock(MainCategoryService.class);
        subCategoryController = new SubCategoryController(mockedSubCategoryService, mockedMainCategoryService);
        request = new MockHttpServletRequest();
    }

    @Test
    public void testSaveSubCategoryWhenMainCategoryNotExist() {
        expect(mockedMainCategoryService.getMainCategoryById(1L)).andReturn(Optional.empty());
        replay(mockedMainCategoryService);

        ResponseEntity<Object> responseEntity = subCategoryController.saveSubCategory(1L, createSubCategory());

        assertEquals(404, responseEntity.getStatusCode().value());

        verify(mockedMainCategoryService);
    }

    @Test
    public void testSaveSubcategoryWhenSubcategoryUnderMainCategory() {
        SubCategory testSubCategory = createSubCategory();
        request.setRequestURI("/budget/mainCategory/1/saveSubCategory");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        expect(mockedMainCategoryService.getMainCategoryById(1L)).andReturn(Optional.of(new MainCategory(MAIN_CATEGORY_NAME)));
        expect(mockedSubCategoryService.addSubCategory(anyObject())).andReturn(testSubCategory);
        replay(mockedMainCategoryService, mockedSubCategoryService);

        ResponseEntity<Object> responseEntity = subCategoryController.saveSubCategory(1L, testSubCategory);

        assertEquals("{Location=[http://localhost/budget/mainCategory/1/saveSubCategory/1]}", responseEntity.getHeaders().toString());
        assertEquals(201, responseEntity.getStatusCode().value());

        verify(mockedMainCategoryService, mockedSubCategoryService);
    }

    @Test
    public void testDeleteSubCategoryWhenFoundDeletedSubCategoryInDB() {
        mockedSubCategoryService.deleteSubCategory(anyLong());
        expectLastCall();
        replay(mockedMainCategoryService);

        subCategoryController.deleteSubCategory(anyLong());

        verify(mockedMainCategoryService);
    }

    @Test
    public void testUpdateSubCategoryWhenSubCategoryFoundInDB() {
        SubCategory testSubCategory = createSubCategory();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);

        expect(mockedSubCategoryService.getSubCategoryById(1L)).andReturn(Optional.of(testSubCategory));
        expect(mockedSubCategoryService.addSubCategory(testSubCategory)).andReturn(testSubCategory);
        replay(mockedSubCategoryService);

        ResponseEntity responseEntity = subCategoryController.updateSubCategory(1L, testSubCategory);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedSubCategoryService);
    }

    @Test
    public void testUpdateSubCategoryWhenSubCategoryNotFoundInDB() {
        SubCategory testSubCategory = createSubCategory();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        expect(mockedSubCategoryService.getSubCategoryById(1L)).andReturn(Optional.empty());
        replay(mockedSubCategoryService);

        ResponseEntity responseEntity = subCategoryController.updateSubCategory(1L, testSubCategory);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedSubCategoryService);
    }

    private SubCategory createSubCategory() {
        SubCategory subCategory = new SubCategory(SUB_CATEGORY_NAME);
        subCategory.setId(1L);
        return subCategory;
    }
}
