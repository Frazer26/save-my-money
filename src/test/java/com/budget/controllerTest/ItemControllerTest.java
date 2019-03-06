package com.budget.controllerTest;

import com.budget.controller.ItemController;
import com.budget.model.Item;
import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.budget.service.ItemService;
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

public class ItemControllerTest {

    private static final String SUB_CATEGORY_NAME = "Household";
    private static final String MAIN_CATEGORY_NAME = "Bills";
    private static final String ITEM_NAME = "Electricity";
    private static final Integer ITEM_VALUE = 5000;


    private SubCategoryService mockedSubCategoryService;
    private MainCategoryService mockedMainCategoryService;
    private ItemService mockedItemService;
    private ItemController itemController;
    private MockHttpServletRequest request;


    @BeforeMethod
    public void setup() {
        mockedSubCategoryService = createMock(SubCategoryService.class);
        mockedMainCategoryService = createMock(MainCategoryService.class);
        mockedItemService = createMock(ItemService.class);
        itemController = new ItemController(mockedItemService, mockedMainCategoryService, mockedSubCategoryService);
        request = new MockHttpServletRequest();
    }

    @Test
    public void testSaveItemInMainCategoryWhenMainCategoryNotExist() {
        expect(mockedMainCategoryService.getMainCategoryById(1L)).andReturn(Optional.empty());
        replay(mockedMainCategoryService);

        ResponseEntity<Object> responseEntity = itemController.saveItemInMainCategory(1L, createItem());

        assertEquals(404, responseEntity.getStatusCode().value());

        verify(mockedMainCategoryService);
    }


    @Test
    public void testSaveItemInMainCategoryWhenItemUnderMainCategory() {
        Item testItem = createItem();
        request.setRequestURI("/budget/mainCategory/1/saveItem");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        expect(mockedMainCategoryService.getMainCategoryById(1L)).andReturn(Optional.of(createMainCategory()));
        expect(mockedItemService.addItem(anyObject())).andReturn(testItem);
        replay(mockedMainCategoryService, mockedItemService);

        ResponseEntity<Object> responseEntity = itemController.saveItemInMainCategory(1L, testItem);

        assertEquals("{Location=[http://localhost/budget/mainCategory/1/saveItem/1]}", responseEntity.getHeaders().toString());
        assertEquals(201, responseEntity.getStatusCode().value());

        verify(mockedMainCategoryService, mockedItemService);
    }

    @Test
    public void testSaveItemInSubCategoryWhenSubCategoryNotExist() {
        expect(mockedSubCategoryService.getSubCategoryById(1L)).andReturn(Optional.empty());
        replay(mockedSubCategoryService);

        ResponseEntity<Object> responseEntity = itemController.saveItemInSubCategory(1L, createItem());

        assertEquals(404, responseEntity.getStatusCode().value());

        verify(mockedSubCategoryService);
    }

    @Test
    public void testSaveItemInSubCategoryWhenItemUnderSubCategory() {
        Item testItem = createItem();
        request.setRequestURI("/budget/subCategory/1/saveItem");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(this.request));

        expect(mockedSubCategoryService.getSubCategoryById(1L)).andReturn(Optional.of(createSubCategory()));
        expect(mockedItemService.addItem(anyObject())).andReturn(testItem);
        replay(mockedSubCategoryService, mockedItemService);

        ResponseEntity<Object> responseEntity = itemController.saveItemInSubCategory(1L, testItem);

        assertEquals("{Location=[http://localhost/budget/subCategory/1/saveItem/1]}", responseEntity.getHeaders().toString());
        assertEquals(201, responseEntity.getStatusCode().value());

        verify(mockedSubCategoryService, mockedItemService);
    }


    @Test
    public void testDeleteItemWhenItemDeletedFromDB() {
        mockedItemService.deleteItem(anyLong());
        expectLastCall();
        replay(mockedItemService);

        itemController.deleteItem(anyLong());

        verify(mockedItemService);
    }


    @Test
    public void testUpdateItemWhenItemFoundInDB() {
        Item testItem = createItem();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.OK);

        expect(mockedItemService.getItemById(1L)).andReturn(Optional.of(testItem));
        expect(mockedItemService.addItem(anyObject())).andReturn(testItem);
        replay(mockedItemService);

        ResponseEntity responseEntity = itemController.updateItem(1L, testItem);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedItemService);
    }

    @Test
    public void testUpdateItemWhenItemNotFoundInDB() {
        Item testItem = createItem();
        ResponseEntity testResponseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);

        expect(mockedItemService.getItemById(1L)).andReturn(Optional.empty());
        replay(mockedItemService);

        ResponseEntity responseEntity = itemController.updateItem(1L, testItem);

        assertEquals(testResponseEntity, responseEntity);

        verify(mockedItemService);
    }

    private Item createItem() {
        Item item = new Item
                .ItemBuilder(ITEM_NAME, ITEM_VALUE)
                .build();
        item.setId(1L);
        return item;
    }

    private SubCategory createSubCategory() {
        return new SubCategory(SUB_CATEGORY_NAME);
    }

    private MainCategory createMainCategory() {
        return new MainCategory(MAIN_CATEGORY_NAME);
    }
}
