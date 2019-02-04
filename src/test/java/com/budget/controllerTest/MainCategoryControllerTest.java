package com.budget.controllerTest;

import com.budget.controller.MainCategoryController;
import com.budget.model.MainCategory;
import com.budget.service.MainCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertEquals;

public class MainCategoryControllerTest {

    private static final String MAIN_CATEGORY_NAME = "Income";

    private MainCategoryService mockedMainCatService;
    private MainCategoryController mainCategoryController;

    @BeforeMethod
    public void setup() {
        mockedMainCatService = createMock(MainCategoryService.class);
        mainCategoryController = new MainCategoryController(mockedMainCatService);
    }

    @Test
    public void testSaveMainCategoryWhenMainCategoryNotEmpty() {
        MainCategory testMainCategory = createTestMainCategory();

        expect(mockedMainCatService.addMainCategory(new MainCategory())).andReturn(testMainCategory);
        replay(mockedMainCatService);

        MainCategory mainCategory = mainCategoryController.saveMainCategory(new MainCategory());

        assertEquals(mainCategory.getName(), testMainCategory.getName());

        verify(mockedMainCatService);
    }

    @Test
    public void testDeleteMainCategoryWhenResponseEntityValue200() {
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);

        mockedMainCatService.deleteMainCategory(anyLong());
        expectLastCall();
        replay(mockedMainCatService);

        ResponseEntity responseEntity = mainCategoryController.deleteMainCategory(anyLong());

        assertEquals(responseEntity, testResponseEntity);

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

        expect(mockedMainCatService.getMainCategoryById((long) 0)).andReturn(Optional.empty());
        replay(mockedMainCatService);

        ResponseEntity responseEntity = mainCategoryController.updateMainCategory(anyLong(), new MainCategory());

        assertEquals(responseEntity, testResponseEntity);

        verify(mockedMainCatService);
    }


    private MainCategory createTestMainCategory() {
        return new MainCategory(MAIN_CATEGORY_NAME);
    }


}
