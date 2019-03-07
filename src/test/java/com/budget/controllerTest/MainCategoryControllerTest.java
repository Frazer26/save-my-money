package com.budget.controllerTest;

import com.budget.controller.MainCategoryController;
import com.budget.model.MainCategory;
import com.budget.service.MainCategoryService;
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

public class MainCategoryControllerTest {

    private static final String MAIN_CATEGORY_NAME = "Income";
    private static final long ID = 1L;
    private static final String SAVE_MAIN_CATEGORY_ENDPOINT = "/budget/saveMainCategory";
    private static final String SAVE_MAIN_CATEGORY_METHOD_RETURNED_URI = "{Location=[http://localhost/budget/saveMainCategory/1]}";
    private static final int STATUS_CODE_201 = 201;

    private MainCategoryService mockedMainCatService;
    private MainCategoryController mainCategoryController;
    private MockHttpServletRequest request;

    @BeforeMethod
    public void setup() {
        mockedMainCatService = createMock(MainCategoryService.class);
        mainCategoryController = new MainCategoryController(mockedMainCatService);
        request = new MockHttpServletRequest();
    }

    @Test
    public void testSaveMainCategoryWhenMainCategoryNotEmpty() {
        MainCategory testMainCategory = createTestMainCategory();
        testMainCategory.setId(ID);
        request.setRequestURI(SAVE_MAIN_CATEGORY_ENDPOINT);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        expect(mockedMainCatService.addMainCategory(testMainCategory)).andReturn(testMainCategory);
        replay(mockedMainCatService);

        ResponseEntity<Object> responseEntity = mainCategoryController.saveMainCategory(testMainCategory);

        assertEquals(SAVE_MAIN_CATEGORY_METHOD_RETURNED_URI, responseEntity.getHeaders().toString());
        assertEquals(STATUS_CODE_201, responseEntity.getStatusCode().value());

        verify(mockedMainCatService);
    }

    @Test
    public void testDeleteMainCategory() {
        mockedMainCatService.deleteMainCategory(ID);
        expectLastCall();
        replay(mockedMainCatService);

        mainCategoryController.deleteMainCategory(ID);

        verify(mockedMainCatService);
    }

    @Test
    public void testUpdateMainCategoryWhenFoundMainCategoryInDB() {
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);
        MainCategory testMainCategory = createTestMainCategory();

        expect(mockedMainCatService.getMainCategoryById(ID)).andReturn(Optional.of(testMainCategory));
        expect(mockedMainCatService.addMainCategory(anyObject())).andReturn(testMainCategory);
        replay(mockedMainCatService);

        ResponseEntity responseEntity = mainCategoryController.updateMainCategory(ID, new MainCategory());

        assertEquals(responseEntity, testResponseEntity);

        verify(mockedMainCatService);
    }

    @Test
    public void testUpdateMainCategoryWhenNotFoundMainCategoryInDB() {
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        expect(mockedMainCatService.getMainCategoryById(ID)).andReturn(Optional.empty());
        replay(mockedMainCatService);

        ResponseEntity responseEntity = mainCategoryController.updateMainCategory(ID, new MainCategory());

        assertEquals(responseEntity, testResponseEntity);

        verify(mockedMainCatService);
    }


    private MainCategory createTestMainCategory() {
        return new MainCategory(MAIN_CATEGORY_NAME);
    }


}
