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
    private static final int STATUS_CODE_201 = 201;
    private static final int STATUS_CODE_404 = 404;
    private static final long ID = 1L;
    private static final String SAVE_SUBCATEGORY_RETURNED_URI = "{Location=[http://localhost/budget/mainCategory/1/saveSubCategoryInMainCategory/1]}";
    private static final String SAVE_SUBCATEGORY_METHOD_ENDPOINT = "/budget/mainCategory/1/saveSubCategoryInMainCategory";

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
        expect(mockedMainCategoryService.getMainCategoryById(ID)).andReturn(Optional.empty());
        replay(mockedMainCategoryService);

        ResponseEntity<Object> responseEntity = subCategoryController.saveSubCategoryInMainCategory(ID, createSubCategory());

        assertEquals(STATUS_CODE_404, responseEntity.getStatusCode().value());

        verify(mockedMainCategoryService);
    }

    @Test
    public void testSaveSubcategoryWhenSubcategoryUnderMainCategory() {
        SubCategory testSubCategory = createSubCategory();
        request.setRequestURI(SAVE_SUBCATEGORY_METHOD_ENDPOINT);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        expect(mockedMainCategoryService.getMainCategoryById(1L)).andReturn(Optional.of(new MainCategory(MAIN_CATEGORY_NAME)));
        expect(mockedSubCategoryService.addSubCategoryUnderMainCategory(anyObject(), anyObject())).andReturn(testSubCategory);
        replay(mockedMainCategoryService, mockedSubCategoryService);

        ResponseEntity<Object> responseEntity = subCategoryController.saveSubCategoryInMainCategory(ID, testSubCategory);

        assertEquals(SAVE_SUBCATEGORY_RETURNED_URI, responseEntity.getHeaders().toString());
        assertEquals(STATUS_CODE_201, responseEntity.getStatusCode().value());

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

        expect(mockedSubCategoryService.getSubCategoryById(ID)).andReturn(Optional.of(testSubCategory));
        expect(mockedSubCategoryService.addSubCategory(testSubCategory)).andReturn(testSubCategory);
        replay(mockedSubCategoryService);

        ResponseEntity responseEntity = subCategoryController.updateSubCategory(ID, testSubCategory);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedSubCategoryService);
    }

    @Test
    public void testUpdateSubCategoryWhenSubCategoryNotFoundInDB() {
        SubCategory testSubCategory = createSubCategory();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        expect(mockedSubCategoryService.getSubCategoryById(ID)).andReturn(Optional.empty());
        replay(mockedSubCategoryService);

        ResponseEntity responseEntity = subCategoryController.updateSubCategory(ID, testSubCategory);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedSubCategoryService);
    }

    private SubCategory createSubCategory() {
        SubCategory subCategory = new SubCategory(SUB_CATEGORY_NAME);
        subCategory.setId(ID);
        return subCategory;
    }
}
