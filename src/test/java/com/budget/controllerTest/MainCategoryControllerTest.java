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
        testMainCategory.setId(1L);
        request.setRequestURI("/budget/saveMainCategory");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        expect(mockedMainCatService.addMainCategory(testMainCategory)).andReturn(testMainCategory);
        replay(mockedMainCatService);

        ResponseEntity<Object> responseEntity = mainCategoryController.saveMainCategory(testMainCategory);

        assertEquals("{Location=[http://localhost/budget/saveMainCategory/1]}", responseEntity.getHeaders().toString());
        assertEquals(201, responseEntity.getStatusCode().value());

        verify(mockedMainCatService);
    }

    @Test
    public void testDeleteMainCategory() {
        mockedMainCatService.deleteMainCategory(anyLong());
        expectLastCall();
        replay(mockedMainCatService);

        mainCategoryController.deleteMainCategory(anyLong());

        verify(mockedMainCatService);
    }

    @Test
    public void testUpdateMainCategoryWhenFoundMainCategoryInDB() {
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);
        MainCategory testMainCategory = createTestMainCategory();

        expect(mockedMainCatService.getMainCategoryById(anyLong())).andReturn(Optional.of(testMainCategory));
        expect(mockedMainCatService.addMainCategory(anyObject())).andReturn(testMainCategory);
        replay(mockedMainCatService);

        ResponseEntity responseEntity = mainCategoryController.updateMainCategory(anyLong(), new MainCategory());

        assertEquals(responseEntity, testResponseEntity);

        verify(mockedMainCatService);
    }

    @Test
    public void testUpdateMainCategoryWhenNotFoundMainCategoryInDB() {
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        expect(mockedMainCatService.getMainCategoryById(0L)).andReturn(Optional.empty());
        replay(mockedMainCatService);

        ResponseEntity responseEntity = mainCategoryController.updateMainCategory(anyLong(), new MainCategory());

        assertEquals(responseEntity, testResponseEntity);

        verify(mockedMainCatService);
    }


    private MainCategory createTestMainCategory() {
        return new MainCategory(MAIN_CATEGORY_NAME);
    }


}
